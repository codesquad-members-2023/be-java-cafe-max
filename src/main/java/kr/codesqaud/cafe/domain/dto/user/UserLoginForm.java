package kr.codesqaud.cafe.domain.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserLoginForm {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,15}$", message = "아이디는 3글자부터 15글자까지의 영문 대소문자와 숫자만 가능합니다.")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "패스워드는 영문과 특수문자 숫자를 포함하며 8글자부터 32자까지만 가능합니다.")
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
