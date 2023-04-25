package kr.codesqaud.cafe.user.dto;

import kr.codesqaud.cafe.user.domain.User;

public class UserUpdateForm {
    private final String userId;
    private final String password;
    private final String userName;
    private final String email;

    public UserUpdateForm(String userId, String password, String userName, String email) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .email(email)
                .build();
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
