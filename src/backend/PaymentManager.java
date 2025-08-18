package backend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PaymentManager {
    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String dateFormatted = today.format(formatter);

    public double calculatePrice(LocalDate startDate, LocalDate endDate, double basePrice) {
        Long days = ChronoUnit.DAYS.between(startDate, endDate);;
        return basePrice * days;
    }
    
    public void addPayment(Payment payment) {
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {

            if(payment.getMethod().equals("card")) {
                try(PreparedStatement prst = con.prepareStatement("UPDATE Guest SET payment_method = ?, card_num = ? WHERE email = ?")) {
                    prst.setString(1,payment.getMethod());
                    prst.setString(2,payment.getCardNum());
                    prst.setString(3,payment.getPaymentID());
                    prst.executeUpdate();
                }
            }
            else {
                try(PreparedStatement prst2 = con.prepareStatement("UPDATE Guest SET payment_method = ?, card_num = ? WHERE email = ?")) {
                    prst2.setString(1,payment.getMethod());
                    prst2.setString(2,null);
                    prst2.setString(3,payment.getPaymentID());
                    prst2.executeUpdate();
                }
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /*public Payment getPaymentInfo(Guest guest) {
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {

            try(PreparedStatement prst = con.prepareStatement("SELECT payment_method, card_num FROM Guest WHERE email = ? ")) {
                prst.setString(1,guest.getEmail());

                try(ResultSet rs = prst.executeQuery()) {
                    if(rs.next()) {
                        if(rs.getString("payment_method").equals("card")) {
                            return new Payment(guest, rs.getString("payment_method"),rs.getString("card_num"));
                        }
                        else if(rs.getString("payment_method").equals("cash")) {
                            return new Payment(guest, rs.getString("payment_method"));
                        }   
                    }
                    else {
                        return new Payment(guest,"cash");
                    }
                }
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Payment(guest, "cash");
    }*/

    public Payment getPaymentInfo(Guest guest) {
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {
            try(PreparedStatement prst = con.prepareStatement("SELECT payment_method, card_num FROM Guest WHERE email = ?")) {
                prst.setString(1, guest.getEmail());

                try (ResultSet rs = prst.executeQuery()) {
                    if (rs.next()) {
                        String method = rs.getString("payment_method");   // may be null
                        String card = rs.getString("card_num");         // may be null

                        if ("card".equalsIgnoreCase(method))
                            return new Payment(guest, "card", card);      // ok if card is null—adjust if needed
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Payment(guest, "cash");
    }

    public void processCardPayment (Payment payment)  {
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {

            try(PreparedStatement prst = con.prepareStatement("INSERT INTO Payment(guest_email, room_type, price, amount_paid, paid, date_paid, method, card_num) VALUES(?,?,?,?,?,?,?,?)")) {
                prst.setString(1, payment.getPaymentID());
                prst.setString(2, payment.getRoomType());
                prst.setDouble(3, payment.getAmount());
                prst.setDouble(4,payment.getAmount());
                prst.setBoolean(5, true);
                prst.setString(6,dateFormatted);
                prst.setString(7,payment.getMethod());
                prst.setString(8,payment.getCardNum());
                prst.executeUpdate();
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void processCashPayment(Payment payment) {
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {

            try(PreparedStatement prst = con.prepareStatement("INSERT INTO Payment(guest_email, room_type, price, amount_paid, paid, date_paid, method, card_num) VALUES(?,?,?,?,?,?,?,?)")) {
                prst.setString(1, payment.getPaymentID());
                prst.setString(2, payment.getRoomType());
                prst.setDouble(3,payment.getAmount());
                prst.setDouble(4,0);
                prst.setBoolean(5, false);
                prst.setString(6, null);
                prst.setString(7,payment.getMethod());
                prst.setString(8,null);
                prst.executeUpdate();
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    /*public void refundPayment (Payment payment) {
        payment.setStatus("Refunded");
        updateStatus(payment);
    } */

    /*public void updateStatus(Payment payment){
        try(Connection con = DriverManager.getConnection("jdbc:sqlite:hotel.db")) {

            // inserting into db
            try(PreparedStatement prst = con.prepareStatement("UPDATE Payment SET status = ? WHERE paymentID = ?")) {
                prst.setString(1, payment.getStatus());
                prst.setString(2, payment.getPaymentID());
                prst.executeUpdate();
            }
        }
        catch (SQLException e) {
            System.out.println("Unable to make reservation. " + e.getMessage());
            e.printStackTrace();
        }
    } */
}
