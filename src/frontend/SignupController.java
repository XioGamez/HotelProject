package frontend;
import java.io.IOException;
import java.sql.SQLException;

import backend.Guest;
import backend.GuestManager;
import backend.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TextField nameText;
    @FXML
    TextField emailText;
    @FXML
    TextField usernameText;
    @FXML
    TextField passwordText;
    @FXML
    Label labelText;

    Guest guest;
    Login log;

    public void back(ActionEvent event) throws IOException{
            Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    public boolean finalCheck() {
        if(nameText.getText().isEmpty() || emailText.getText().isEmpty() || usernameText.getText().isEmpty() || passwordText.getText().isEmpty()) {
            return false;
        }
        return true;
    } 
    public void Signup(ActionEvent event) throws IOException, SQLException {
        if(finalCheck()) {
            log = new Login(usernameText.getText(),passwordText.getText());
            guest = new Guest(nameText.getText(), emailText.getText());
            GuestManager g1 = new GuestManager();
            
            if(g1.signUp(log,guest)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("payment_info.fxml"));
                root = loader.load();

                PaymentInfoController paymentInfoController = loader.getController();
                paymentInfoController.setGuest(guest);

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else {
                labelText.setText("You are already a member");
            }
        }
        else {
            labelText.setText("Please enter all required information");
        }      
    }
}
