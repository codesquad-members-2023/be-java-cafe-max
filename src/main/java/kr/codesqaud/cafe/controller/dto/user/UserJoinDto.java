package kr.codesqaud.cafe.controller.dto.user;

import kr.codesqaud.cafe.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserJoinDto {
    @NotBlank(message = "아이디를 입력하세요.")
    @Size(min = 5, max = 20, message = "아이디는 {min} ~ {max} 길이로 입력하세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 알파벳, 숫자만 가능합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 {min} ~ {max} 길이로 입력하세요.")
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_+|~=`{}\\[\\]:\";'\\\\<>,.?/\\-]+", message = "비밀번호는 알파벳, 숫자, 특수문자만 가능합니다.")
    private String password;

    @NotBlank(message = "닉네임을 입력하세요.")
    @Size(min = 2, max = 20, message = "닉네임은 {min} ~ {max} 길이로 입력하세요.")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]+$", message = "닉네임 형식에 맞게 입력하세요.")
    private String nickname;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    public UserJoinDto(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public UserJoinDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User toUser() {
        return new User(username, nickname, password, email);
    }
}
