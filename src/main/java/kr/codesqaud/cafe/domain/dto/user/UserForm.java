package kr.codesqaud.cafe.domain.dto.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserForm {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,15}$", message = "아이디는 3글자부터 15글자까지의 영문 대소문자와 숫자만 가능합니다.")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;
    @Length(min = 2, max = 10, message = "이름은 2글자부터 10글자까지만 가능합니다.")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "패스워드는 영문과 특수문자 숫자를 포함하며 8글자부터 32자까지만 가능합니다.")
    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    public UserForm() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
