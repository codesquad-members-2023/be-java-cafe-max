package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.dto.UserListResponse;
import kr.codesqaud.cafe.dto.UserProfileResponse;

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

    public UserListResponse toListResponse() {
        return new UserListResponse(id, userId, name, email);
    }

    public UserProfileResponse toProfileResponse() {
        return new UserProfileResponse(name, email);
    }
}
