package backend;
public class Guest {
    private String name;
    private String email;
    private int partySize;
    public Guest(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public Guest(String name, String email, int partySize) {
        this.name = name;
        this.email = email;
        this.partySize = partySize;

    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public int getPartySize() {
        return partySize;
    }
    public void setPartySize(int s) {
        partySize = s;
    }
}