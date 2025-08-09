import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReservationManager {

    public Reservation getReservation(Guest guest, String checkIn, String checkOut)  { 
        
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {                    
            Hotel room;

            try(PreparedStatement prst = con.prepareStatement("SELECT * FROM Reservation WHERE email = ? AND checkIn = ? AND checkOut = ?")) {
                prst.setString(1,guest.getEmail());
                prst.setString(2,checkIn);
                prst.setString(3,checkOut);
            
                try(ResultSet rs = prst.executeQuery()) {
                    if(rs.next()) {

                        if(rs.getString("Room_Type").equals("Standard")) {
                            room = new Standard();
                            guest.setPartySize(rs.getInt("PartySize"));
                            return new Reservation(guest, room, checkIn,checkOut);
                        }
                        if(rs.getString("Room_Type").equals("Deluxe"))  {
                            room = new Deluxe();
                            guest.setPartySize(rs.getInt("PartySize"));
                            return new Reservation(guest, room, checkIn,checkOut);
                        }
                        else {
                            room = new Suite();
                            guest.setPartySize(rs.getInt("PartySize"));
                            return new Reservation(guest, room, checkIn,checkOut);
                        }
                    }
                    
                }
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void updateReservation(Reservation newReservation, Reservation oldReservation)  {

        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {
            
            if(newReservation.getRoom().getRoomType().equals(oldReservation.getRoom().getRoomType())) {
                String roomType = newReservation.getRoom().getRoomType();
                newReservation.setOccupancy(true);
                
                try(PreparedStatement prst = con.prepareStatement("UPDATE " + roomType + " SET occupied = ?, guest_email = ? " + "WHERE room_num = (SELECT room_num FROM " + roomType + " WHERE room_num BETWEEN 1 AND 10 AND occupied IS NULL AND guest_email IS NULL " + "LIMIT 1)");) {
                    prst.setBoolean(1, newReservation.getOccupancy());
                    prst.setString(2, newReservation.getGuest().getEmail());
                    prst.executeUpdate();
                }
                try(PreparedStatement prst2 = con.prepareStatement("Update Reservation SET email = ?, name = ?, partySize = ?, checkIn = ?, checkOut = ?, Room_Type = ? WHERE email = ?")) {
                    prst2.setString(1, newReservation.getGuest().getEmail());
                    prst2.setString(2, newReservation.getGuest().getName());
                    prst2.setInt(3,newReservation.getGuest().getPartySize());
                    prst2.setString(4, newReservation.getCheckIn());
                    prst2.setString(5, newReservation.getCheckOut());
                    prst2.setString(6,newReservation.getRoom().getRoomType());
                    prst2.setString(7,oldReservation.getGuest().getEmail());
                    prst2.executeUpdate();
                }
            }
            else {
                String newRoomType = newReservation.getRoom().getRoomType();
                String oldRoomType = oldReservation.getRoom().getRoomType();
                newReservation.setOccupancy(true);

                try(PreparedStatement prst3 = con.prepareStatement("UPDATE " + oldRoomType + " SET occupied = ?, guest_email = ? WHERE guest_email = ?");) {
                    prst3.setBoolean(1, false);
                    prst3.setString(2,null);
                    prst3.setString(3,oldReservation.getGuest().getEmail());
                    prst3.executeUpdate();
                }
                try(PreparedStatement prst = con.prepareStatement("UPDATE " + newRoomType + " SET occupied = ?, guest_email = ? " + "WHERE room_num = (SELECT room_num FROM " + newRoomType + " WHERE room_num BETWEEN 1 AND 10 AND occupied = 0 AND guest_email IS NULL " + "LIMIT 1)");) {
                    prst.setBoolean(1, newReservation.getOccupancy());
                    prst.setString(2, newReservation.getGuest().getEmail());
                    prst.executeUpdate();
                }
                try(PreparedStatement prst2 = con.prepareStatement("Update Reservation SET email = ?, name = ?, partySize = ?, checkIn = ?, checkOut = ?, Room_Type = ? WHERE email = ?")) {
                    prst2.setString(1, newReservation.getGuest().getEmail());
                    prst2.setString(2, newReservation.getGuest().getName());
                    prst2.setInt(3,newReservation.getGuest().getPartySize());
                    prst2.setString(4, newReservation.getCheckIn());
                    prst2.setString(5, newReservation.getCheckOut());
                    prst2.setString(6,newReservation.getRoom().getRoomType());
                    prst2.setString(7,oldReservation.getGuest().getEmail());
                    prst2.executeUpdate();
                }
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addReservation(Reservation reservations) {
    
            try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {
                
                // inserting into db
                try(PreparedStatement prst = con.prepareStatement("INSERT INTO Reservation(email, name, partySize, checkIn, checkOut, Room_Type) VALUES(?, ?, ?, ?, ?, ?);");) {
                    prst.setString(1, reservations.getGuest().getEmail());
                    prst.setString(2, reservations.getGuest().getName());
                    prst.setInt(3, reservations.getGuest().getPartySize());
                    prst.setString(4, reservations.getCheckIn());
                    prst.setString(5, reservations.getCheckOut());
                    prst.setString(6,reservations.getRoom().getRoomType());
                    prst.executeUpdate();
                }

                // Updating room db
                try(PreparedStatement prst2 = con.prepareStatement("UPDATE " + reservations.getRoom().getRoomType() + " SET occupied = ?, guest_email = ? " + "WHERE room_num = (SELECT room_num FROM " + reservations.getRoom().getRoomType() + " WHERE room_num BETWEEN 1 AND 10 AND occupied IS NULL AND guest_email IS NULL " + "LIMIT 1)");){
                    prst2.setBoolean(1, true);
                    prst2.setString(2, reservations.getGuest().getEmail());
                    prst2.executeUpdate();
                    System.out.println("Reservation added.");
                }

                // add payment to db
                // add Payment payment as a parameter
                /*
                try(PreparedStatement prst3 = con.prepareStatement("INSERT INTO Payment(paymentId, method, amount, date, status) VALUES(?, ?, ?, ?, ?);)")) {
                    prst3.setString(reservations.getGuest().getEmail());
                    prst3.setString(2, payment.getMethod());
                    prst3.setDouble(3, payment.getAmount());
                    prst3.setString(4, payment.getDate());
                    prst3.setString(5, payment.getStatus());
                    prst3.executeUpdate();
                }
                 */
            }
            
        catch (SQLException e) {
            System.out.println("Unable to make reservation. " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void printReservationInfo(Reservation reservations) {
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {
            PreparedStatement prst = con.prepareStatement("SELECT guest.id, guest.name, guest.partySize, guest.checkIn, guest.checkOut, rm.roomTyoe, rm.price FROM Reservation guest JOIN Room rm ON guest.room_num = rm.id WHERE r.id = ?");
            prst.setString(1, reservations.getGuest().getEmail());
            ResultSet rs = prst.executeQuery();

            if (!rs.next())
                System.out.println("Reservation not found.");

            String email = rs.getString("guest.id");
            String name = rs.getString("guest.name");
            int partySize = rs.getInt("guest.partySize");
            String checkIn = rs.getString("guest.checkIn");
            String checkOut = rs.getString("guest.checkOut");
            String roomType = rs.getString("rm.roomType");
            double price = rs.getInt("rm.price");

            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);
            long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

            System.out.println(name);
            System.out.println(email);
            System.out.println(partySize);
            System.out.println(roomType);
            System.out.println(price);
            System.out.println(checkInDate);
            System.out.println(checkOutDate);
            System.out.println(nights);
            System.out.println(price * nights);

            rs.close();
            prst.close();
        }
        catch (SQLException e) {
            System.out.println("Unable to print reservation info. " + e.getMessage());
            e.printStackTrace();
        }
    }
}