package kr.codesqaud.cafe.user.dto;

import javax.validation.constraints.Size;
import kr.codesqaud.cafe.user.User;

public class LoginRequestDto {

    @Size(min = 1, max = 10)
    private String loginId;

    @Size(min = 8, max = 10)
    private String password;

    public LoginRequestDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public User toEntity() {
        return new User.Builder()
                .loginId(loginId)
                .password(password)
                .build();
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

}
