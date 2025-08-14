package frontend;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.animation.Interpolator;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.animation.*;

public class HomeController {
    
    private Stage stage;
    private Scene scene;

    @FXML private Button   loginMainButton;
    @FXML private Button   signupMainButton;
    @FXML private ImageView logoImageView;

    @FXML
    public void initialize() {
        //LC: Pop-in only for logo + login + signup buttons
        popFromBottom(logoImageView,   0); //LC: no delay
        popFromBottom(loginMainButton, 120); // 120ms delay
        popFromBottom(signupMainButton,240); // 240ms delay
    }

    private void popFromBottom(Node node, double delayMs) {
        //LC: Start
        node.setOpacity(0);
        node.setTranslateY(60); // how far below to start

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
