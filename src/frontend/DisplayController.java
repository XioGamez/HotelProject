package frontend;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import backend.Deluxe;
import backend.Guest;
import backend.Hotel;
import backend.Payment;
import backend.PaymentManager;
import backend.Reservation;
import backend.ReservationManager;
import backend.Standard;
import backend.Suite;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class DisplayController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Reservation oldReservation;
    private Reservation newReservation;
    private Guest guest;
    Hotel room;
    Payment payment;

    @FXML
    Label oldRoomLabel;
    @FXML
    Label oldCheckInLabel;
    @FXML
    Label oldCheckOutLabel;
    
    @FXML
    RadioButton standardButton, deluxeButton, suiteButton;
    @FXML
    ToggleGroup toggleGroup;
    @FXML
    Label promptLabel;
    @FXML
    DatePicker check_In, check_Out;
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String checkInText;
    String checkOutText;


    @FXML
    public void initialize() {
        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if(newToggle == standardButton) {
                if(check_In.getValue() == null || check_Out.getValue() == null) {
                    display("Please pick a check-in and check-out date");
                }
                else {
                    display("Your total cost for a reservation with Standard room type from " + getCheckIn() + " to " + getCheckOut() + " is " + "$" + calculatePrice(100)  );
                }    
            }

            else if(newToggle == deluxeButton) {
                if(check_In.getValue() == null || check_Out.getValue() == null) {
                    display("Please pick a check-in and check-out date");
                }
                else {
                    display("Your total cost for a reservation with Deluxe room type from " + getCheckIn() + " to " + getCheckOut() + " is " + "$" + calculatePrice(150)  );
                }
            }

            else if(newToggle == suiteButton) {
                if(check_In.getValue() == null || check_Out.getValue() == null) {
                    display("Please pick a check-in and check-out date");
                }
                else {
                    display("Your total cost for a reservation with Suite room type from " + getCheckIn() + " to " + getCheckOut() + " is " +"$" + calculatePrice(200)  );
                }
            }

            else if(getStartDate()!= null || getEndDate() != null && (!standardButton.isSelected() && !deluxeButton.isSelected() && !suiteButton.isSelected())) {
                display("Please enter all required information");
            }
        });
    }
    
    // Update the reservation, tied to 'Update Reservation' button, call 2 helper methods finalCheck(), and finalizeReservation()
    public void update(ActionEvent event) throws IOException {
        if(finalCheck()) {
            finalizeReservation();

            this.newReservation = new Reservation(guest,room,getCheckIn(),getCheckOut());
            ReservationManager r = new ReservationManager();
            r.updateReservation(newReservation,oldReservation);

            PaymentManager pm = new PaymentManager();
            if(payment.getMethod().equals("card")) {
                pm.processCardPayment(payment);
            }
            else {
                pm.processCashPayment(payment);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReservationConfirmation.fxml"));
            root = loader.load();

            ReservationConfirmationController rc = loader.getController();
            rc.setGuest(this.guest);
            rc.setPayment(this.payment);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            promptLabel.setText("Please enter all required informations");
        }
         
    }
    // Check to see if all required information has been entered
    public boolean finalCheck() {
        if((standardButton.isSelected() || deluxeButton.isSelected() || suiteButton.isSelected()) && check_In.getValue() != null && check_Out.getValue() != null) {
            return true;
        }
        return false;
    }
    // Finalize all reservation related information
    public void finalizeReservation() {
        if(standardButton.isSelected()) {
            this.room = new Standard();
            this.payment.setRoomType("Standard");
            this.payment.setAmount(calculatePrice(100));
        }
        else if(deluxeButton.isSelected()) {
            this.room = new Deluxe();
            this.payment.setRoomType("Deluxe");
            this.payment.setAmount(calculatePrice(150));
        }
        else if(suiteButton.isSelected()) {
            this.room = new Suite();
            this.payment.setRoomType("Suite");
            this.payment.setAmount(calculatePrice(200));
        }
    }

    //Cancel the reservation, tied to 'Cancel Reservation' button
    public void CancelReservation(ActionEvent event) throws IOException{
        ReservationManager r1 = new ReservationManager();
        r1.CancelReservation(oldReservation);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReservationConfirmation.fxml"));
        root = loader.load();

        ReservationConfirmationController rc = loader.getController();
        rc.setGuest(this.guest);
        rc.setPayment(this.payment);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }

    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        root = loader.load();

        MenuController mc = loader.getController();
        mc.setGuest(this.guest);
        mc.setPayment(this.payment);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void display(String str) {
        promptLabel.setText(str);
    }
    public LocalDate getStartDate() {
         return check_In.getValue();
    }
    public LocalDate getEndDate() {
        return check_Out.getValue();
    }
    public String getCheckIn() {
        LocalDate localDate = check_In.getValue();
        return localDate.format(formatter);
    }
    public String getCheckOut() {
        LocalDate localDate = check_Out.getValue();
        return localDate.format(formatter);
    }
    public void setHotelRoom(Hotel room) {
        this.room = room;
    }
    public double calculatePrice(double basePrice) {
        PaymentManager pm = new PaymentManager();
        return pm.calculatePrice(getStartDate(), getEndDate(), basePrice);
    }
    public void setOldReservation(Reservation oldreservation) {
        this.oldReservation = oldreservation;
    }
    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    public void setPayment(Payment p) {
        this.payment = p;
    }
    public void display(){
        oldRoomLabel.setText(oldReservation.getRoom().getRoomType());
        oldCheckInLabel.setText(oldReservation.getCheckIn());
        oldCheckOutLabel.setText(oldReservation.getCheckOut());
    }
    public void print() {
        System.out.println(payment.getPaymentID() + " " + payment.getMethod() + " " + payment.getCardNum());
        System.out.println(payment.getAmount());
    }

    

    


