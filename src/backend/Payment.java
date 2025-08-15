package backend;
public class Payment {
    private String paymentId;
    private String method;
    private double amount;
    private String date;
    private boolean status;
    private String cardNum;
    private String room_type;

    // Constructor for guest with card payment
    public Payment(Guest guest, String method, String cardNum) {
        this.paymentId = guest.getEmail();
        this.method = method;
        this.cardNum = cardNum;
    }

    // Constructor for guest with cash payment
    public Payment(Guest guest, String method) {
        this.paymentId = guest.getEmail();
        this.method = method;
    }

    
    public Payment(Guest guest, String method, String cardNum, String date, String room_type) {
        this.paymentId = guest.getEmail();
        this.method = method;
        this.date = date;
        this.status = true;
        this.room_type = room_type;
        this.cardNum = cardNum;
    }

    
    public Payment(Guest guest, String method, String date, String room_type) {
        paymentId = guest.getEmail();
        this.method = method;
        this.date = date;
        this.status = false;
        this.room_type = room_type;
    }
    public String getPaymentID() {
        return paymentId;
    }
    public String getMethod() {
        return method;
    }
    public void setAmount(double a) {
        this.amount = a;
    }
    public double getAmount() {
        return amount;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String s) {
        this.date = s;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean getStatus() {
        return status;
    }
    public void setRoomType(String s) {
        this.room_type = s;
    }
    public String getRoomType() {
        return this.room_type;
    }
    public String getCardNum() {
        return this.cardNum;
    }
}