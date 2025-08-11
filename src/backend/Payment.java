package backend;
public class Payment {
    private String paymentId;
    private String method;
    private double amount;
    private String date;
    private String status;

    public Payment(Reservation reservation, String method, double amount, String date) {
        paymentId = reservation.getGuest().getEmail();
        this.method = method;
        this.amount = amount;
        this.date = date;
        status = "Pending";
    }
    public String getPaymentID() {
        return paymentId;
    }
    public String getMethod() {
        return method;
    }
    public double getAmount() {
        return amount;
    }
    public String getDate() {
        return date;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}