import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Hotel {
    
    Connection con;

    public Hotel() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:hotel.db");
            PreparedStatement pragma = con.prepareStatement("PRAGMA foreign_keys = ON;");
            pragma.execute();
            System.out.println("con.isValid(0) = " + con.isValid(0));
        }
        catch(SQLException e) {
            System.out.println("You fucked up somewhere");
        }
    }

    public void addReservation(Guest guest) throws SQLException {
        
        try {
            PreparedStatement prst = con.prepareStatement("SELECT name FROM Guest WHERE name = ?");
            prst.setString(1, guest.getName());
            ResultSet rs = prst.executeQuery();
        
            if(!rs.next()) {
                prst = con.prepareStatement("INSERT INTO Guest(id,name,partySize) VALUES(?,?,?);");
                prst.setString(1,guest.getEmail());
                prst.setString(2,guest.getName());
                prst.setInt(3,guest.getPartySize());
                prst.executeUpdate();

                prst = con.prepareStatement("INSERT INTO " + guest.getRoomType() + "(room_num,occupied,guest_id) VALUES(?,?,?)");
                prst.setInt(1,1);
                prst.setBoolean(2,true);
                prst.setString(3,guest.getEmail());
                prst.executeUpdate();
            }
            
            else {
                prst = con.prepareStatement("INSERT INTO " + guest.getRoomType() + "(room_num,occupied,guest_id) VALUES(?,?,?)");
                prst.setInt(1,1);
                prst.setBoolean(2,true);
                prst.setString(3,guest.getEmail());
                prst.executeUpdate();

            }      
            System.out.println("A room has been reserved for you");
          
        }
        catch(SQLException e) {
            System.out.println("Unable to book a room");
            e.printStackTrace();
        }

    }
}
