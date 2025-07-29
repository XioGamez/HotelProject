public class Guest {
    private String name;
    private int partySize;
    private String email;
    private String roomType;

    public Guest(String s1, int num, String s2) {
        this.name = s1;
        this.partySize = num;
        this.email = s2;
    }

    public String getName() {
        return this.name;
    }

    public int getPartySize() {
        return this.partySize;
    }

    public String getEmail() {
        return this.email;
    }

    public void setRoomType(String s) {
        this.roomType = s;
    }

    public String getRoomType() {
        return this.roomType;
    }
}
