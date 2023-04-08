package kr.codesqaud.cafe.controller.dto.request;

public class JoinRequest {
    private final String userId;
    private final String password;
    private final String userName;
    private final String userEmail;

    public JoinRequest(String userId, String password, String userName, String userEmail) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
