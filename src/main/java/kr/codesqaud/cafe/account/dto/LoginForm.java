package kr.codesqaud.cafe.account.dto;

import kr.codesqaud.cafe.account.annotation.ValidEmail;
import kr.codesqaud.cafe.account.annotation.ValidPassword;

public class LoginForm {
    @ValidEmail
    private final String email;
    @ValidPassword
    private final String password;

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
