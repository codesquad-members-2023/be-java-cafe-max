package kr.codesqaud.cafe.controller.dto.user;

import kr.codesqaud.cafe.domain.User;

public class LoginUserDto {
    private final Long id;
    private final String userId;

    public LoginUserDto(Long id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public LoginUserDto(User user) {
        this(user.getId(), user.getUserId());
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }
}
