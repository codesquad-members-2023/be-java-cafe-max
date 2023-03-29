package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.dto.UserResponse;

public class User {

    private final long id;
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public UserResponse toResponse() {
        return new UserResponse(id, userId, name, email);
    }
}
