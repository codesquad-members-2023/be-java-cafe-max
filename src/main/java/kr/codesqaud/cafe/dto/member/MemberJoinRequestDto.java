package kr.codesqaud.cafe.dto.member;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import kr.codesqaud.cafe.domain.Member;

public class MemberJoinRequestDto {
    @NotBlank(message = "이메일을 입력하세요")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min = 8, max = 12, message = "비밀번호는 {min}~{max} 길이로 입력하세요.")
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_+|~=`{}\\[\\]:\";'\\\\<>,.?/\\-]+", message = "비밀번호는 알파벳, 숫자, 특수문자만 가능합니다.")
    private String password;

    @NotBlank(message = "닉네임을 입력하세요")
    @Size(min = 2, max = 10, message = "유효한 닉네임을 입력하세요")
    @Pattern(regexp = "^[가-힣]{2,4}$", message = "닉네임 형식에 맞게 입력하세요.")
    private String nickName;

    public MemberJoinRequestDto() {
    }

    public MemberJoinRequestDto(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Member toUser() {
        return new Member(this.email, this.password, this.nickName, LocalDateTime.now());
    }
}
