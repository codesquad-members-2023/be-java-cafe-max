package kr.codesqaud.cafe.web.dto.user;

import kr.codesqaud.cafe.domain.user.User;

public class UserResponseDto {

    private final Long id;
    private final String userId;
    private final String name;
    private final String email;

    public UserResponseDto(User user) {
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
