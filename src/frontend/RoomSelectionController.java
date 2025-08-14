package frontend;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import backend.Deluxe;
import backend.Guest;
import backend.Hotel;
import backend.Payment;
import backend.Reservation;
import backend.ReservationManager;
import backend.Standard;
import backend.Suite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class RoomSelectionController {
    private Stage stage;
    private Scene scene;

    Hotel room;
    Guest guest;
    Reservation r;
    ReservationManager rm;
    Payment payment;

    @FXML
    DatePicker check_In;
    @FXML
    DatePicker check_Out;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String checkInText;
    String checkOutText;

    @FXML
    public void initialize() {}

    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setReservation_Standard(ActionEvent event) throws IOException {
        room = new Standard();
        reservationConfirmation(event);
    }

    public void setReservation_Deluxe(ActionEvent event) throws IOException  {
        room = new Deluxe();
        reservationConfirmation(event);
    }

    public void setReservation_Suite(ActionEvent event) throws IOException {
        room = new Suite();
        reservationConfirmation(event);
    }

    public void reservationConfirmation(ActionEvent event) throws IOException {
        r = new Reservation(guest,room,checkInText,checkOutText);
        rm = new ReservationManager();
        rm.addReservation(r);

        Parent root = FXMLLoader.load(getClass().getResource("ReservationConfirmation.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = loader.load();

        MenuController rsc = loader.getController();
        rsc.setGuest(this.guest);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getCheckIn(ActionEvent event) throws IOException {
        LocalDate localDate = check_In.getValue();
        checkInText = localDate.format(formatter);
    }
    public void getCheckOut(ActionEvent event) throws IOException {
        LocalDate localDate = check_Out.getValue();
        checkOutText = localDate.format(formatter);
    }
}
