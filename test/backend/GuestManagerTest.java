package backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class GuestManagerTest {

    private GuestManager guestManager = new GuestManager();

    @BeforeEach
    void setup() throws SQLException {
        guestManager = new GuestManager();
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {
            con.createStatement().execute("DELETE FROM Guest  WHERE name = 'andrew';");
            con.createStatement().execute("DELETE FROM login WHERE username = 'andrew';");
        }
    }

    @Test
    void testSignUp_NewUser() throws SQLException {
        Guest guest = new Guest ("andrew", "twinkletoes@gmail.com", 3);
        Login login = new Login ("andrew", "greenfrog");
        guestManager.signUp(login, guest);

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM login WHERE username = 'andrew';");
            assertTrue(rs.next());
            assertEquals("twinkletoes@gmail.com", rs.getString("user_email"));
        }
    }

    @Test
    void testSignUp_ExistingUser() throws SQLException {
        Guest guest = new Guest ("andrew", "twinkletoes@gmail.com", 3);
        Login login = new Login ("andrew", "greenfrog");
        guestManager.signUp(login, guest);
        assertFalse(guestManager.signUp(login,guest));

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) as count FROM login WHERE username = 'andrew'");
            assertEquals(1, rs.getInt("count"));
        }
    }

    @Test
    void testLogin_Success() throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {
            con.createStatement().execute("INSERT INTO login VALUES ('andrew', 'greenfrog', '2067508519');");
        }
        Login login = new Login ("andrew", "greenfrog");
        assertTrue(guestManager.login(login));
    }

    @Test
    void testLogin_WrongUsername() throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {
            con.createStatement().execute("INSERT INTO login VALUES ('andrew', 'greenfrog', '2067508519');");
        }
        Login login = new Login ("neil", "greenfrog");
        assertFalse(guestManager.login(login));
    }

    @Test
    void testLogin_WrongPassword() throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {
            con.createStatement().execute("INSERT INTO login VALUES ('andrew', 'greenfrog', '2067508519');");
        }
        Login login = new Login ("andrew", "purpleduck");
        assertFalse(guestManager.login(login));
    }

    @Test
    void testLogin_NoUser() throws SQLException {
        Login login = new Login ("andrew", "greenfrog");
        assertFalse(guestManager.login(login));
    }

    @Test
    void testGetInfo() throws SQLException {
        Guest guest = new Guest ("andrew", "twinkletoes@gmail.com", 3);
        Login login = new Login("andrew", "greenfrog");
        guestManager.signUp(login, guest);

        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {
           ResultSet rs = con.createStatement().executeQuery("SELECT * FROM login WHERE username = 'andrew';");
           assertTrue(rs.next());
           assertEquals("andrew", rs.getString("username"));
           assertEquals("twinkletoes@gmail.com", rs.getString("user_email"));
           assertEquals("2067508519", rs.getString("hash"));

           rs =  con.createStatement().executeQuery("SELECT * FROM Guest WHERE name = 'andrew';");
           assertTrue(rs.next());
           assertEquals("andrew", rs.getString("name"));
           assertEquals("twinkletoes@gmail.com", rs.getString("email"));
           assertEquals(3, rs.getInt("partySize"));
        }
    }
}