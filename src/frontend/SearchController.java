package frontend;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import backend.Guest;
import backend.Payment;
import backend.Reservation;
import backend.ReservationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class SearchController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Guest guest;
    private Reservation oldReservation;
    private Payment payment;
    private ReservationManager reservationManager = new ReservationManager();

    @FXML
    private TextField emailText;
    @FXML
    private DatePicker checkInDate;
    @FXML
    private DatePicker checkOutDate;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String checkinText;
    String checkoutText;

    public void setPayment(Payment p) {
        this.payment = p;
    }
    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void print() {
        System.out.println(payment.getPaymentID() + " " + payment.getMethod() + " " + payment.getCardNum());
        System.out.println(payment.getAmount() + " " + payment.getDate());
    }
    
    public void searchReservation(ActionEvent event) throws IOException {
       this.oldReservation = reservationManager.getReservation(guest, checkinText, checkoutText);
       FXMLLoader loader = new FXMLLoader(getClass().getResource("display.fxml"));
       root = loader.load();
        
       DisplayController displayController = loader.getController();
       displayController.setGuest(this.guest);
       displayController.setOldReservation(this.oldReservation);
       displayController.setPayment(this.payment);
       displayController.display();

       stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       scene = new Scene(root);
       stage.setScene(scene);
       stage.show(); 

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

    public void getCheckIn(ActionEvent event) throws IOException {
        LocalDate localDate = checkInDate.getValue();
        checkinText = localDate.format(formatter);
    }
    public void getCheckOut(ActionEvent event) throws IOException {
        LocalDate localDate = checkOutDate.getValue();
        checkoutText = localDate.format(formatter);
    }
}
