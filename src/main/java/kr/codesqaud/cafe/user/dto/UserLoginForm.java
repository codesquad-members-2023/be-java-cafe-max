package kr.codesqaud.cafe.user.dto;

public class UserLoginForm {
    private final String userId;
    private final String password;

    public UserLoginForm(String userId, String password) {
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
