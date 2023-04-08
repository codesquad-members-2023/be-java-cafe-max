package kr.codesqaud.cafe.account.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginForm {
    @NotEmpty
    @Email
    private final String email;
    @NotEmpty
    @Size(max = 32, min = 8, message = "{error.password.size}")
    @Pattern(regexp = "^(.*[a-z]+.*[1-9]+가.*)|(.*[1-9]+.*[a-z]+.*)$", message = "{error.password.pattern}")
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
