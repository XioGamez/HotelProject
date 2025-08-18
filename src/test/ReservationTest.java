package test;
import backend.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;


public class ReservationTest {
    
   @Test
    public void testAddReservation_guestName() {
        var guest = new Guest("Batman","Bruce Wayne",3);
        var room = new Standard();
        var reservation = new Reservation(guest, room, "03/24/2000","03/25/2000");
        var rm = new ReservationManager();
        rm.addReservation(reservation);
        var testReservation = rm.getReservation(guest,"03/24/2000","03/25/2000");
        assertEquals(reservation.getGuest().getName(),testReservation.getGuest().getName());
    }

    @Test
    public void testAddReservation_checkIn() {
        var guest = new Guest("Nightwing","Dick Grayson",3);
        var room = new Standard();
        var reservation = new Reservation(guest, room, "03/24/2000","03/25/2000");
        var rm = new ReservationManager();
        rm.addReservation(reservation);
        var testReservation = rm.getReservation(guest,"03/24/2000","03/25/2000");
        assertEquals(reservation.getCheckIn(),testReservation.getCheckIn());
    }

    @Test
    public void testAddReservation_checkOut() {
        var guest = new Guest("Red Hood","Jason Todd",3);
        var room = new Standard();
        var reservation = new Reservation(guest, room, "03/24/2000","03/25/2000");
        var rm = new ReservationManager();
        rm.addReservation(reservation);
        var testReservation = rm.getReservation(guest,"03/24/2000","03/25/2000");
        assertEquals(reservation.getCheckOut(),testReservation.getCheckOut());
    }

    @Test
    public void testUpdateReservation_newReservation() {
        var guest = new Guest("Batgirl","Barbara Gordon",3);
        var oldRoom = new Standard();
        var oldReservation = new Reservation(guest, oldRoom, "03/24/2000","03/25/2000");
        var rm = new ReservationManager();
        rm.addReservation(oldReservation);
        var newRoom = new Deluxe();
        var newReservation = new Reservation(guest, newRoom,"03/24/2000","03/25/2000" );
        rm.updateReservation(newReservation, oldReservation);
        var testReservation = rm.getReservation(guest,"03/24/2000","03/25/2000");

        assertEquals("Deluxe", testReservation.getRoom().getRoomType());
    }

    @Test
    public void testUpdateReservation_oldReservation() {
        var guest = new Guest("Red Robin","Tim Drake",3);
        var oldRoom = new Standard();
        var oldReservation = new Reservation(guest, oldRoom, "03/24/2000","03/25/2000");
        var rm = new ReservationManager();
        rm.addReservation(oldReservation);
        var newRoom = new Deluxe();
        var newReservation = new Reservation(guest, newRoom,"04/12/2020","04/21/2020" );
        rm.updateReservation(newReservation, oldReservation);

        assertNull(rm.getReservation(guest,"03/24/2000","03/25/2000" ));
    }
}
