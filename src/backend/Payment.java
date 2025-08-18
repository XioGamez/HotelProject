package backend;
public abstract class Payment {
    private final String paymentId;
    private final String method;
    private double amount;
    private String cardNum;
    private String room_type;

    // Constructor for guest with card payment
    public Payment(Guest guest, String method, String cardNum) {
        paymentId = guest.getEmail();
        this.method = method;
        this.cardNum = cardNum;
    }

    // Constructor for guest with cash payment
    public Payment(Guest guest, String method) {
        paymentId = guest.getEmail();
        this.method = method;
    }

    public String getPaymentID() {
        return paymentId;
    }

    public abstract String getMethod();

    public void setAmount(double a) {
        this.amount = a;
    }
    public double getAmount() {
        return amount;
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