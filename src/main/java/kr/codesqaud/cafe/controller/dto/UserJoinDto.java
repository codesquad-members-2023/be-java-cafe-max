package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.User;

public class UserJoinDto {
    private String userId;
    private String name;
    private String password;
    private String email;

    public UserJoinDto(String userId, String name, String password, String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User toUser() {
        return new User(userId, name, password, email);
    }
}
