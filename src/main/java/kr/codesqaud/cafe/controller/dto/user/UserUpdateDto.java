package kr.codesqaud.cafe.controller.dto.user;

import kr.codesqaud.cafe.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserUpdateDto {
    private Long id;

    @NotBlank(message = "닉네임을 입력하세요.")
    @Size(min = 2, max = 20, message = "닉네임은 {min} ~ {max} 길이로 입력하세요.")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]+$", message = "닉네임 형식에 맞게 입력하세요.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 {min} ~ {max} 길이로 입력하세요.")
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_+|~=`{}\\[\\]:\";'\\\\<>,.?/\\-]+", message = "비밀번호는 알파벳, 숫자, 특수문자만 가능합니다.")
    private String password;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 {min} ~ {max} 길이로 입력하세요.")
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_+|~=`{}\\[\\]:\";'\\\\<>,.?/\\-]+", message = "비밀번호는 알파벳, 숫자, 특수문자만 가능합니다.")
    private String newPassword;

    public UserUpdateDto(Long id, String nickname, String password, String newPassword) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.newPassword = newPassword;
    }

    public UserUpdateDto() {
    }

    public UserUpdateDto(UserReadDto userReadDto) {
        this(userReadDto.getId(), userReadDto.getNickname(), null, null);
    }

    public User toUser() {
        return new User(id, nickname, newPassword);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
