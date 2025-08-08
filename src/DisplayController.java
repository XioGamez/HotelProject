import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class DisplayController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Reservation oldReservation;
    private Reservation newReservation;

    @FXML
    TextField oldRoomText;

    @FXML
    TextField oldCheckInText;

    @FXML
    TextField oldCheckOutText;

    @FXML
    TextField newRoomText;

    @FXML
    TextField newCheckInText;

    @FXML
    TextField newCheckOutText;

    public void setOldReservation(Reservation oldreservation) {
        oldReservation = oldreservation;
    }



}
