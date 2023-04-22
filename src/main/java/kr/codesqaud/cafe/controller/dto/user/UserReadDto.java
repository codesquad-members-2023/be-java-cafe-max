package kr.codesqaud.cafe.controller.dto.user;

import kr.codesqaud.cafe.domain.User;

public class UserReadDto {
    private final Long id;
    private final String username;
    private final String nickname;
    private final String email;

    public UserReadDto(Long id, String username, String nickname, String email) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
    }

    public UserReadDto(User user) {
        this(user.getId(), user.getUsername(), user.getNickname(), user.getEmail());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }
}
