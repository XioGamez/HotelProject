import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) throws Exception {
        //launch(args);
            Guest guest = new Guest("Bruce Wayne","Batman@batmail.com",1);
            Hotel room = new Standard();
            Reservation r = new Reservation(guest,room, "03/24/2000","24/03/2000");
            ReservationManager rm = new ReservationManager();
            rm.addReservation(r);

    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }
}
