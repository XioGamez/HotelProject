import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) throws Exception {
        //launch(args);
        Hotel hotel = new Hotel();
        Guest guest = new Guest("Kyle",1,"kyletruong2000@gmail.com");
        guest.setRoomType("Standard");
        hotel.addReservation(guest);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }
}
