package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import backend.*;
public class GuestTest {
    /*@Test
    public void newSignUp() throws SQLException {
        Guest guest = new Guest("Andrew", "twinkletoes");
        Login log = new Login("Andrew@greenfrog.gmail","ABC123");
        GuestManager gm = new GuestManager();
        gm.signUp(log, guest);

        Guest testGuest = gm.getInfo(log);
        assertEquals(guest.getName(),testGuest.getName());
    }*/
        
    /*@Test
    public void checkUser() throws SQLException {
        Guest guest = new Guest("Barbara", "twinkletoes2");
        Login log = new Login("Barbara@greenfrog.gmail","ABC1234");
        GuestManager gm = new GuestManager();
        gm.signUp(log, guest);
        
        assertFalse(gm.signUp(log, guest));
    }*/

   /* / @Test
    public void testLogin_username() throws SQLException {
        Guest guest = new Guest("Dick Grayson", "Nightwing");
        Login log = new Login("DG.gmail.com","12345");
        GuestManager gm = new GuestManager();
        gm.signUp(log, guest);

        assertTrue(gm.login(log));
    } */

    @Test
    public void testLogin_password() throws SQLException {
        Guest guest = new Guest("Barbara Gordon", "Batgirl");
        Login log = new Login("BG.gmail.com","1234567");
        GuestManager gm = new GuestManager();
        gm.signUp(log, guest);
        Login testLog = new Login("BG.gmail.com","12345678");

        assertFalse(gm.login(testLog));
    }

}   
