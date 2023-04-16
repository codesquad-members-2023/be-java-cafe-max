package kr.codesqaud.cafe.user.dto;

import kr.codesqaud.cafe.user.domain.User;

public class UserAddForm {
    private final String userId;
    private final String password;
    private final String userName;
    private final String email;

    public UserAddForm(String userId, String password, String userName, String email) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
    }

    public User toUser() {
        return new User(userId, password, userName, email);
    }

    public UserResponse toUserResponse() {
        return new UserResponse(userId, userName, email);
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

    public String getEmail() {
        return email;
    }
}
