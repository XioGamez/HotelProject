import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class SearchController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Guest guest;
    private Reservation oldReservation;
    private ReservationManager reservationManager = new ReservationManager();

    @FXML
    private TextField emailText;
    @FXML
    private TextField checkinText;
    @FXML
    private TextField checkoutText;

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void searchReservation(ActionEvent event) throws IOException {
        String email = emailText.getText();
        String checkin = checkinText.getText();
        String checkout = checkoutText.getText();

        this.oldReservation = reservationManager.getReservation(guest, checkin, checkout);

    }
}
