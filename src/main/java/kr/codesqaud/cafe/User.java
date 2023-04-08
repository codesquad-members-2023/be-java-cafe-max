package kr.codesqaud.cafe;

public class User {
    private String userId;
    private String password;
    private String userEmail;

    public User(String userId, String password, String userEmail) {
        this.userId = userId;
        this.password = password;
        this.userEmail = userEmail;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserEmail() {
        return userEmail;
    }
}




















