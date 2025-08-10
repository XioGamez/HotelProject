import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentManager {
    public boolean processPayment (Payment payment) {
        if (payment.getAmount() <= 0) {
            payment.setStatus("Failed");
            updateStatus(payment);
        }
        payment.setStatus("Completed");
        updateStatus(payment);
        return true;
    }
    public void refundPayment (Payment payment) {
        payment.setStatus("Refunded");
        updateStatus(payment);
    }
    public void updateStatus(Payment payment){
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
    }
}
