package kr.codesqaud.cafe.user.dto;

import kr.codesqaud.cafe.user.annotation.ValidEmail;
import kr.codesqaud.cafe.user.annotation.ValidPassword;

public class LoginForm {
    @ValidEmail
    private String email;
    @ValidPassword
    private String password;


    public LoginForm() {
    }

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
