package kr.codesqaud.cafe.dto;

public class LoginForm {

    private final String userId;
    private final String password;

    public LoginForm(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
