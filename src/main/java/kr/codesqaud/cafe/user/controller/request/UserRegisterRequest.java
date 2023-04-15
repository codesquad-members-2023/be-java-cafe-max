package kr.codesqaud.cafe.user.controller.request;

import kr.codesqaud.cafe.user.service.User;

public class UserRegisterRequest {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public UserRegisterRequest(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toUser() {
        return new User(null ,userId, password, name, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
