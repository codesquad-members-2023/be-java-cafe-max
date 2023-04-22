package kr.codesqaud.cafe.user.controller.request;

public class UserLoginRequest {

    private final String userId;
    private final String password;

    public UserLoginRequest(String userId, String password) {
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
