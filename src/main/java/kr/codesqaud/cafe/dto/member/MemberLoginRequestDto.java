package kr.codesqaud.cafe.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MemberLoginRequestDto {
    @NotBlank(message = "이메일을 입력하세요")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min = 8, max = 12, message = "비밀번호는 {min}~{max} 길이로 입력하세요.")
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_+|~=`{}\\[\\]:\";'\\\\<>,.?/\\-]+", message = "비밀번호는 알파벳, 숫자, 특수문자만 가능합니다.")
    private String password;

    public MemberLoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
