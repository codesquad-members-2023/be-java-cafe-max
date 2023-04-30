package kr.codesqaud.cafe.dto.member;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import kr.codesqaud.cafe.domain.Member;
import org.hibernate.validator.constraints.Length;

public class SignUpRequest {

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[a-zA-Z0-9]{8,32}$")
    private final String password;

    @NotBlank
    @Length(min = 2, max = 10)
    private final String nickname;

    private final LocalDateTime createDateTime;

    public SignUpRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createDateTime = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public Member toMember() {
        return new Member(email, password, nickname, createDateTime);
    }
}
