import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Hotel {
    
    Connection con;

    public Hotel() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:hotel.db");
            System.out.println("con.isValid(0) = " + con.isValid(0));
        }
        catch(SQLException e) {
            System.out.println("You fucked up somewhere");
        }
    }
}
