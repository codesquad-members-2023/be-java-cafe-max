package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import kr.codesqaud.cafe.domain.Member;
import org.hibernate.validator.constraints.Length;

public class SignUpRequest {

    @NotBlank
    @Pattern(regexp = "[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,3}$")
    private final String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[a-zA-Z0-9]{8,32}$")
    private final String password;

    @NotBlank
    @Length(min = 2, max = 10)
    private final String nickName;

    public SignUpRequest(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
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
        return new Member(UUID.randomUUID().toString(), email, password, nickName,
            LocalDateTime.now());
    }
}
