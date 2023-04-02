package kr.codesqaud.cafe.web.dto;

import javax.validation.constraints.Pattern;
import kr.codesqaud.cafe.domain.user.User;

public class UserSavedRequestDto {

    @Pattern(regexp = "^[a-z\\d_-]{5,20}$", message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
    private String userId;
    @Pattern(regexp = "[A-Za-z\\d!@#$%^&*()_+]{8,16}$", message = "8~16자 영문 대 소문자, 숫자, 특수문자만 사용 가능합니다.")
    private String password;
    @Pattern(regexp = "^[a-z가-힣]{1,20}$", message = "1~20자 영문 소문자, 한글만 사용 가능합니다.")
    private String name;
    @Pattern(regexp = "[a-z\\d]+@[a-z]+\\.[a-z]{2,3}", message = "(소문자 또는 숫자로 최소1글자)@(소문자 최소1글자).(소문자 2~3글자) 형식으로 사용 가능합니다.")
    private String email;

    public UserSavedRequestDto() {
    }

    public User toEntity() {
        return new User(userId, password, name, email);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("UserSavedRequestDto{userId=%s, password=%s, name=%s, email=%s}",
            userId, password, name, email);
    }
}
