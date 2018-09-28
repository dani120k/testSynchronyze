package syncronization.model;

public class Admin {
    public Admin(String userPrincipalName, String password) {
        this.userPrincipalName = userPrincipalName;
        this.password = password;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    private String userPrincipalName;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
}
