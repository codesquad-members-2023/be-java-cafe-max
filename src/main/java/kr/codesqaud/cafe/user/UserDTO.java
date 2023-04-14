package kr.codesqaud.cafe.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * 회원 DTO 객체
 */
public class UserDTO {

    @Size(min = 1, max = 10)
    private String userId;

    @Size(min = 8, max = 10)
    private String password;

    @Size(min = 1, max = 10)
    private String name;

    @Email
    private String email;

    public User toEntity() {
        return new User.Builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .build();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
