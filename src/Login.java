public class Login {
    String username;
    String password;
    int hash;

    public void setUsername(String u) {
        this.username = u;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public int getHashcode() {
        return this.password.hashCode();
    }
}
