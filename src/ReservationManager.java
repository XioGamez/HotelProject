import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ReservationManager {

    public void addReservation(Reservation reservations) {
    
            try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {
                
                // inserting into db
                try(PreparedStatement prst = con.prepareStatement("INSERT INTO Reservation(email, name, partySize, checkIn, checkOut) VALUES(?,?,?, ?, ?);");) {
                    prst.setString(1, reservations.getGuest().getEmail());
                    prst.setString(2, reservations.getGuest().getName());
                    prst.setInt(3, reservations.getGuest().getPartySize());
                    prst.setString(4, reservations.getCheckIn());
                    prst.setString(5, reservations.getCheckOut());
                    prst.executeUpdate();
                }

                // Updating room db
                try(PreparedStatement prst2 = con.prepareStatement("UPDATE " + reservations.getRoom().getRoomType() + " SET occupied = ?, guest_email = ? " + "WHERE room_num = (SELECT room_num FROM " + reservations.getRoom().getRoomType() + " WHERE room_num BETWEEN 1 AND 10 AND occupied IS NULL AND guest_email IS NULL " + "LIMIT 1)");){
                    prst2.setBoolean(1, true);
                    prst2.setString(2, reservations.getGuest().getEmail());
                    prst2.executeUpdate();
                    System.out.println("Reservation added.");
                }
            }
            
        catch (SQLException e) {
            System.out.println("Unable to make reservation. " + e.getMessage());
            e.printStackTrace();
        }
    }
}