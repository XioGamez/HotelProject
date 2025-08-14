package frontend;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.layout.AnchorPane;

public class HomeController {
    
    private Stage stage;
    private Scene scene;

    @FXML private Button   loginMainButton;
    @FXML private Button   signupMainButton;
    @FXML private ImageView logoImageView;

    @FXML
    public void initialize() {
        //LC: Staggered entrance animations
        popFromBottom(logoImageView,   0); //LC: logo first
        popFromBottom(loginMainButton, 120); // then login
        popFromBottom(signupMainButton,240); // then signup

        //Purple hover glow only on hover (no base glow)
        addHoverGlow(loginMainButton);
        addHoverGlow(signupMainButton);
    }

    //LC: Slide-up + fade-in for any node
    private void popFromBottom(Node node, double delayMs) {
        node.setOpacity(0);
        node.setTranslateY(60); // LC: how far below to start

        TranslateTransition slide = new TranslateTransition(Duration.millis(700), node);
        slide.setFromY(60);
        slide.setToY(0);
        slide.setInterpolator(Interpolator.EASE_OUT);
        
        FadeTransition fade = new FadeTransition(Duration.millis(700), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition combo = new ParallelTransition(slide, fade);
        combo.setDelay(Duration.millis(delayMs));
        combo.play();
    }

    //LC: Hover-only purple glow for buttons
    private void addHoverGlow(Button b) {
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(0);
        shadow.setOffsetY(0);

        //LC: Start with no effect at rest
        b.setEffect(null);

        //LC: Animate from no glow -> glow
        Timeline hoverIn = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(shadow.radiusProperty(), 1),
                new KeyValue(shadow.spreadProperty(), 0.0),
                new KeyValue(shadow.colorProperty(), Color.web("#8A2BE2", 0.0))
            ),
            new KeyFrame(Duration.millis(160),
                new KeyValue(shadow.radiusProperty(), 24, Interpolator.EASE_OUT),
                new KeyValue(shadow.spreadProperty(), 0.40, Interpolator.EASE_OUT),
                new KeyValue(shadow.colorProperty(), Color.web("#8A2BE2", 1.0), Interpolator.EASE_OUT)
            ) 
        );

        //LC: Animate back to no glow, then effect will remove entirely
        Timeline hoverOut = new Timeline(
            new KeyFrame(Duration.millis(160),
                new KeyValue(shadow.radiusProperty(), 1, Interpolator.EASE_IN),
                new KeyValue(shadow.spreadProperty(), 0.0, Interpolator.EASE_IN),
                new KeyValue(shadow.colorProperty(), Color.web("#8A2BE2", 0.0), Interpolator.EASE_IN)
            )
        );
        hoverOut.setOnFinished(e -> b.setEffect(null));

        b.setOnMouseEntered(e -> { 
            hoverOut.stop();
            //LC: attach effect and animate
            b.setEffect(shadow);
            hoverIn.playFromStart();
        });

        b.setOnMouseExited(e -> { 
            hoverIn.stop();
            hoverOut.playFromStart();
        });
    }

    public void logIn(ActionEvent event) throws IOException {
            
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        
        }
    public void signUp(ActionEvent event) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
}
