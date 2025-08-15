package frontend;
import java.io.IOException;
import java.sql.SQLException;

import backend.Guest;
import backend.GuestManager;
import backend.Login;
import backend.Payment;
import backend.PaymentManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TextField userNameText;
    @FXML
    TextField passwordText;
    @FXML
    TextField printText;
   
    Guest guest;
    Login log;
    Payment payment;
    public void back(ActionEvent event) throws IOException{
            Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    public void logIn(ActionEvent event) throws IOException, SQLException  {
            PaymentManager p = new PaymentManager();
            GuestManager g1 = new GuestManager();
            log = new Login(userNameText.getText(),passwordText.getText());
            
            if(g1.login(log)){ 
                guest = g1.getInfo(log);
                payment = p.getPaymentInfo(guest);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));	
		        root = loader.load();	
                
                MenuController menuController = loader.getController();
                menuController.setGuest(guest);
                menuController.setPayment(payment);
                
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else { 
                printText.setText("Wrong information");
            }
        }
    }
