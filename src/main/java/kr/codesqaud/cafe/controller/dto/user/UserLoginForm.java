package kr.codesqaud.cafe.controller.dto.user;

import javax.validation.constraints.NotBlank;

public class UserLoginForm {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId;
    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    public UserLoginForm() {
    }

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
