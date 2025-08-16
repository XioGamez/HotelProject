package frontend;
import java.io.IOException;
import java.sql.SQLException;
import java.util.EventObject;

import backend.Guest;
import backend.Payment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MenuController {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    Guest guest;
    Payment payment;
    
    public void logout(ActionEvent event) throws IOException{
            Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void search(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("search.fxml"));
        root = loader.load();
        
        SearchController searchController = loader.getController();
        searchController.setGuest(this.guest);
        searchController.setPayment(this.payment);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void makeReservation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomSelection.fxml"));	
        root = loader.load();

        RoomSelectionController rsc = loader.getController();
        rsc.setGuest(this.guest);
        rsc.setPayment(this.payment);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
