package backend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private String username;
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public Login(String s1) {
        this.username = s1;
    }
    public void setUsername(String u) {
        this.username = u;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public int getHashcode() {
        return this.password.hashCode();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUserEmail() {
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {

            try(PreparedStatement prst = con.prepareStatement("SELECT * FROM login WHERE username = ?")) {
                prst.setString(1, this.getUsername());

                try(ResultSet rs = prst.executeQuery()) {
                    if(rs.next()) {
                        return rs.getString(2);
                    }
                }
            }
        }
    catch(SQLException e) {
        System.out.println(e.getMessage());
    }
    return null;
    }

}
