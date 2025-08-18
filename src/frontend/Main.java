package frontend;
import backend.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
         launch(args);

        // Leave this here for testing reservation related methods.
        
        //Guest guest = new Guest("Bruce Wayne","Batman.inc",3);
        /*Login log = new Login("Batman","Robin");
        GuestManager gm = new GuestManager();
        gm.signUp(log, guest);
        /*Hotel room = new Standard();
        Reservation r = new Reservation(guest,room,"24/03/2000","03/24/2000");
        ReservationManager rm = new ReservationManager();
        rm.addReservation(r); 

        /*Guest guest = new Guest("Bruce Wayne","Batman.inc",3);
        ReservationManager r = new ReservationManager();
        Reservation oldReservation = r.getReservation(guest,"24/03/2000","03/24/2000");
        Hotel room = new Deluxe();
        Reservation newReservation = new Reservation(guest,room,"08/16/1985","16/08/1988");
        r.updateReservation(newReservation, oldReservation);
        //r.CancelReservation(newReservation); */
        //Payment payment = new Payment(guest,"card","232323232");
        /*PaymentManager pm = new PaymentManager();
        Payment newPayment = pm.getPaymentInfo(guest);
        System.out.println(newPayment.getCardNum() + " " + newPayment.getPaymentID());*/

    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/frontend/home.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
    }
}
