import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class DisplayController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Reservation oldReservation;
    private Reservation newReservation;
    private Guest guest;

    @FXML
    Label oldRoomLabel;
    @FXML
    Label oldCheckInLabel;
    @FXML
    Label oldCheckOutLabel;
    
    @FXML
    TextField newRoomText;
    @FXML
    TextField newCheckInText;
    @FXML
    TextField newCheckOutText;

    public void setOldReservation(Reservation oldreservation) {
        this.oldReservation = oldreservation;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void display(){
        oldRoomLabel.setText(oldReservation.getRoom().getRoomType());
        oldCheckInLabel.setText(oldReservation.getCheckIn());
        oldCheckOutLabel.setText(oldReservation.getCheckOut());
    }


}
