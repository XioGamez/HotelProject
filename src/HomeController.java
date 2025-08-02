import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class HomeController {
    
    private Stage stage;
    private Scene scene;

    @FXML
    private void loginMainButton(ActionEvent event) {
        switchScene(event, "login.fxml");
    }

    @FXML
    private void signupMainButton(ActionEvent event) {
        switchScene(event, "signup.fxml");
    }

    public void switchScene(ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
