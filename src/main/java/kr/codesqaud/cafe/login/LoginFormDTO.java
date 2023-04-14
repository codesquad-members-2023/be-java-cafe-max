package kr.codesqaud.cafe.login;

import javax.validation.constraints.Size;

public class LoginFormDTO {

    @Size(min = 1, max = 10)
    private String userId;

    @Size(min = 8, max = 10)
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
