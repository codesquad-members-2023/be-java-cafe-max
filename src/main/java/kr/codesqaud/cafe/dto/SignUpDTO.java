package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.User;

public class SignUpDTO {
    private String userId;
    private String password;
    private String passwordCheck;
    private String name;
    private String email;

    public SignUpDTO(String userId, String password, String passwordCheck, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public User convertToUser(int idx) {
        return new User(idx, userId, password, name, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", passwordCheck='" + passwordCheck + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
