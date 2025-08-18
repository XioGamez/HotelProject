package backend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestManager {

    public boolean signUp(Login log, Guest guest) throws SQLException {
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {

            try(PreparedStatement prst = con.prepareStatement("SELECT user_email FROM login WHERE user_email = ?")) {
                prst.setString(1,guest.getEmail());
                
                    try(ResultSet rs = prst.executeQuery()) {
                        if(!rs.next()) {

                        try(PreparedStatement prst2 = con.prepareStatement("INSERT INTO login(username,user_email,hash) VALUES(?,?,?)")) {
                            prst2.setString(1, log.getUsername());
                            prst2.setString(2, guest.getEmail());
                            prst2.setInt(3,log.getHashcode());
                            prst2.executeUpdate();
                        }

                        try(PreparedStatement prst3 = con.prepareStatement("INSERT INTO Guest(email,name,partySize) VALUES(?,?,?)")) {
                            prst3.setString(1,guest.getEmail());
                            prst3.setString(2,guest.getName());
                            prst3.setInt(3,guest.getPartySize());
                            prst3.executeUpdate();
                        }
                        return true;
                    }
                    else {
                        System.out.println("You are already a member");
                        return false;
                    }
                }
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(Login log) throws SQLException {
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {

            try(PreparedStatement prst = con.prepareStatement("SELECT * FROM login WHERE username = ?")) {
                prst.setString(1,log.getUsername());

                try(ResultSet rs = prst.executeQuery()) {
                    if(!rs.next()) {
                        return false;
                    }
                    return rs.getInt("hash") == log.getHashcode();
                }
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Guest getInfo(Login log) throws SQLException {
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {

            try(PreparedStatement prst = con.prepareStatement("SELECT * FROM Guest WHERE email = ?")) {
                prst.setString(1,log.getUserEmail());

                try(ResultSet rs = prst.executeQuery()) {
                    if(rs.next()) {
                        return new Guest(rs.getString("name"),rs.getString("email"));
                    }
                }
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
    public Login getLogin(Guest guest) {
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {

            try(PreparedStatement prst = con.prepareStatement("SELECT * FROM login WHERE email = ?")) {
                prst.setString(1,guest.getEmail());

                try(ResultSet rs = prst.executeQuery()) {
                    if(rs.next()) {
                        return new Login(rs.getString("username"));
                    }
                }
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    */
}
