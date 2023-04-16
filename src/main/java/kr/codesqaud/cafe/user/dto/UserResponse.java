package kr.codesqaud.cafe.user.dto;

import kr.codesqaud.cafe.user.domain.User;

public class UserResponse {
    private final String userId;
    private final String userName;
    private final String email;

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

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
