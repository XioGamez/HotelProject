public class Reservation {
    private Guest guest;
    private Hotel room;
    private String checkIn;
    private String checkOut;

    public Reservation (Guest guest, Hotel room, String checkIn, String checkOut) {
        this.guest = guest;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
    public Guest getGuest() {
        return guest;
    }
    public Hotel getRoom() {
        return room;
    }
    public String getCheckIn() {
        return checkIn;
    }
    public String getCheckOut() {
        return checkOut;
    }
}