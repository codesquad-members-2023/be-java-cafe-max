package kr.codesqaud.cafe.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * 회원 DTO 객체
 */
public class SignUpRequestDto {

    @Size(min = 1, max = 10)
    private String loginId;

    @Size(min = 8, max = 10)
    private String password;

    @Size(min = 1, max = 10)
    private String name;

    @Email
    private String email;

    public SignUpRequestDto(String loginId, String password, String name, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        return new User.Builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .email(email)
                .build();
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
