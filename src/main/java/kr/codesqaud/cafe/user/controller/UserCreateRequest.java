package kr.codesqaud.cafe.user.controller;

public class UserCreateRequest {
    private String userId;
    private String name;
    private String email;
    private String password;


    public UserCreateRequest(String userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;

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

    public String getPassword() {
        return password;
    }
}
