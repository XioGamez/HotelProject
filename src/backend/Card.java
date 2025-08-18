package backend;

public class Card extends Payment {
    public Card(Guest guest, String cardNum) {
        super(guest, "Card",cardNum);
    }

    public String getMethod() {
        return "Card";
    }
}
