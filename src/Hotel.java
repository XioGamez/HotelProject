import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
