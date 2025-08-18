package backend;

public class Cash extends Payment {
    public Cash (Guest guest) {
        super(guest, "Cash");
    }

    public String getMethod() {
        return "Cash";
    }
}
