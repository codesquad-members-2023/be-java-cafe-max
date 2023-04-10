package kr.codesqaud.cafe.user.dto;

import kr.codesqaud.cafe.user.domain.User;

public class UserResponse {
    private String userId;
    private String userName;
    private String email;

    public UserResponse(String userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getUserId(), user.getUserName(), user.getEmail());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
