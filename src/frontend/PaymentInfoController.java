package frontend;

import java.io.IOException;

import backend.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class PaymentInfoController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    RadioButton cardButton, cashButton;
    @FXML 
    TextField cardNumText;
    @FXML
    Label textLabel;
    @FXML
    ToggleGroup toggleGroup;
    
    Guest guest;
    Payment payment;

    @FXML
    public void initialize() {
        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if(newToggle == cardButton) {
                cardNumText.setVisible(true);
                textLabel.setText("Please note that for card payment, you will be charged the moment you confirm your reservation with this program");
            }
            else if(newToggle == cashButton) {
                cardNumText.setVisible(false);
                textLabel.setText("Please note that for cash payment, you have to check in by 12:00 PM on your check in date, otherwise, your reservation will be cancelled");
            }
            else {
                cardNumText.setVisible(false);
            }
        });
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    public void finalizePaymentInfo() {
        if(cardButton.isSelected()) {
            this.payment = new Card(guest, cardNumText.getText());
            PaymentManager p = new PaymentManager();
            p.addPayment(payment); 
        }
        else if(cashButton.isSelected()) {
            this.payment = new Cash(guest);
            PaymentManager p = new PaymentManager();
            p.addPayment(payment); 
        }
    }
    public void addPayment(ActionEvent event) throws IOException {
            finalizePaymentInfo();
            
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
}
