package kr.codesqaud.cafe.app.user.controller.dto;

import kr.codesqaud.cafe.app.user.entity.User;

public class UserResponse {

    private final Long id;
    private final String userId;
    private final String name;
    private final String email;

    public UserResponse(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("UserResponseDto={id=%d, userId=%s, name=%s, email=%s}",
            id, userId, name, email);
    }
}
