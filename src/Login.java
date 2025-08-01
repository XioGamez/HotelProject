public class Login {
    private String username;
    private String password;
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

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
