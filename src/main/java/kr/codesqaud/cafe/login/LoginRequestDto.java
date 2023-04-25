package kr.codesqaud.cafe.login;

import javax.validation.constraints.Size;

public class LoginRequestDto {

    @Size(min = 1, max = 10)
    private String loginId;

    @Size(min = 8, max = 10)
    private String password;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
