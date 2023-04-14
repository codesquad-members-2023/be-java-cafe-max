package kr.codesqaud.cafe.dto;

public class UserResponse {
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserResponse(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public UserResponse() {
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
