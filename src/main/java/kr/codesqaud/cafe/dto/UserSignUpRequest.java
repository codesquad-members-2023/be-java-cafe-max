package kr.codesqaud.cafe.dto;

import org.springframework.lang.NonNull;

public class UserSignUpRequest {
    private String userId;
    @NonNull
    private String password;
    @NonNull
    private String name;
    @NonNull
    private String email;

    public UserSignUpRequest(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
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
}
