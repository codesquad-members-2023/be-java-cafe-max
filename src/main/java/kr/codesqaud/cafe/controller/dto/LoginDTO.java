package kr.codesqaud.cafe.controller.dto;

public class LoginDTO {

    private final String userId;
    private final String password;

    public LoginDTO(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

}
