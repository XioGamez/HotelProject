package frontend;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeController {
    
    private Stage stage;
    private Scene scene;

    @FXML private Button   loginMainButton;
    @FXML private Button   signupMainButton;
    @FXML private ImageView logoImageView;

    //LC: responsive background sizing
    @FXML private StackPane centerStack;
    @FXML private ImageView bgImage;

    @FXML
    public void initialize() {
        if (bgImage !=null && centerStack != null) {
            bgImage.fitWidthProperty().bind(centerStack.widthProperty());
            bgImage.fitHeightProperty().bind(centerStack.heightProperty());
        }

        //LC: Staggered entrance animations
        if (logoImageView != null) popFromBottom(logoImageView, 0); //LC: logo first
        if (loginMainButton != null) popFromBottom(loginMainButton, 120); // then login
        if (signupMainButton != null) popFromBottom(signupMainButton,240); // then signup
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
