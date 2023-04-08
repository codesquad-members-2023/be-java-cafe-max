package kr.codesqaud.cafe;

public class User {
    private String userId;
    private String password;
    private String email;       // form에서 사용된 name에 맞춤

    public User(String userId, String password, String email) {
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}




















