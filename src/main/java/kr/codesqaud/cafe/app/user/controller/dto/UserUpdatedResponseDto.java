package kr.codesqaud.cafe.app.user.controller.dto;

import kr.codesqaud.cafe.app.user.entity.User;

public class UserUpdatedResponseDto {

    private Long id;
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserUpdatedResponseDto(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("UserResponseDto={id=%d, userId=%s, password=%s name=%s, email=%s}",
            id, userId, password, name, email);
    }

}
