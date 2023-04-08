package kr.codesqaud.cafe.controller.dto.user;

import kr.codesqaud.cafe.domain.User;

public class UserReadDto {
    private final Long id;
    private final String userId;
    private final String name;
    private final String email;

    public UserReadDto(Long id, String userId, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public UserReadDto(User user) {
        this(user.getId(), user.getUserId(), user.getName(), user.getEmail());
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
}
