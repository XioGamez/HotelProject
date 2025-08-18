package frontend;

import backend.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PrintInfoController {
    private Stage stage;
    private Scene scene;

    private Guest guest;
    private Reservation reservation;
    private Payment payment;

    @FXML
    Label printInfo;

    public void getInfo() {
        if(reservation != null) {
            ReservationManager rm = new ReservationManager();
            String info = rm.printReservationInfo(reservation);
            printInfo.setText(info);
        } else
            printInfo.setText("No reservation information found for " + guest.getName());
    }

    public void backButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = loader.load();

        MenuController mc = loader.getController();
        mc.setGuest(this.guest);
        mc.setPayment(this.payment);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        getInfo();
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
