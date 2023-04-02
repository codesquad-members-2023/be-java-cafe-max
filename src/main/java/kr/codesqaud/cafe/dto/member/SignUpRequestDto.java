package kr.codesqaud.cafe.dto.member;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import kr.codesqaud.cafe.domain.Member;

public class SignUpRequestDto {
    @NotBlank
    @Pattern(regexp = "[a-z\\d]+@[a-z\\d]+\\.[a-z]{2,3}$")
    private final String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])[a-zA-Z\\d]{8,32}$")
    private final String password;

    @NotBlank
    @Length(min = 2, max = 10)
    private final String nickName;

    private final LocalDateTime createDate;

    public SignUpRequestDto(String email, String password, String nickName, LocalDateTime createDate) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createDate = createDate;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Member toEntity(){
        return new Member(UUID.randomUUID().toString(),email,password,nickName,createDate);
    }
}
