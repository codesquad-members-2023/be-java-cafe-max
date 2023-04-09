package kr.codesqaud.cafe.dto.member;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import kr.codesqaud.cafe.domain.Member;

public class SignUpRequestDto {
    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])[a-zA-Z\\d]{8,32}$")
    private final String password;

    @NotBlank
    @Length(min = 2, max = 10)
    private final String nickName;

    private final LocalDateTime createDate;

    public SignUpRequestDto(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createDate = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public Member toEntity() {
        return new Member(email, password, nickName, createDate);
    }
}
