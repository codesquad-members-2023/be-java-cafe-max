package kr.codesqaud.cafe.controller.dto.login;

public class AuthenticationDTO {

    private final String userId;
    private final String password;

    public AuthenticationDTO(String userId, String password) {
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
