import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestManager {
    
    Connection con;

    public GuestManager() {
        connect();
    }

    public void connect() {
        try {
        con = DriverManager.getConnection("jdbc:sqlite:hotel.db");
        PreparedStatement pragma = con.prepareStatement("PRAGMA foreign_keys = ON;");
        pragma.execute();
        System.out.println("Connected to database");
        }
        
        catch(SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public void signUp(Login log, Guest guest) throws SQLException {
        try {
            if (con == null || con.isClosed()) {
                connect();
            }

            PreparedStatement prst = con.prepareStatement("SELECT user_id FROM login WHERE user_id = ?");
            prst.setString(1,guest.getEmail());
            ResultSet rs = prst.executeQuery();
            if(!rs.next()) {

                prst = con.prepareStatement("INSERT INTO login(username,user_id,hash) VALUES(?,?,?)");
                prst.setString(1, log.getUsername());
                prst.setString(2, guest.getEmail());
                prst.setInt(3,log.getHashcode());
                prst.executeUpdate();

                prst = con.prepareStatement("Insert INTO Guest(id,name,partySize) VALUES(?,?,?)");
                prst.setString(1,guest.getEmail());
                prst.setString(2,guest.getName());
                prst.setInt(3,guest.getPartySize());
            }
            else {
                System.out.println("You are already a member");
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        con.close();
    }

    public Boolean login(Login log) throws SQLException {
        try {
            if (con == null || con.isClosed()) {
                connect();
            }
            PreparedStatement prst = con.prepareStatement("SELECT username, password-hash FROM login WHERE username = ?");
            prst.setString(1,log.getUsername());
            ResultSet rs = prst.executeQuery();

            if(!rs.next()) {
                rs.close();
                prst.close();
                con.close();
                return false;
            }
            else {
                if(rs.getInt(3) != log.getHashcode()) {
                    rs.close();
                    prst.close();
                    con.close();
                    return false;
                }
                else {
                    rs.close();
                    prst.close();
                    con.close();
                    return true;
                }
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
}
