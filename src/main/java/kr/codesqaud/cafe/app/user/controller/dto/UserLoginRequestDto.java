package kr.codesqaud.cafe.app.user.controller.dto;

import javax.validation.constraints.Pattern;
import kr.codesqaud.cafe.app.user.entity.User;

public class UserLoginRequestDto {

    @Pattern(regexp = "^[a-z\\d_-]{5,20}$", message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
    private final String userId;
    @Pattern(regexp = "[A-Za-z\\d!@#$%^&*()_+]{8,16}$", message = "8~16자 영문 대 소문자, 숫자, 특수문자만 사용 가능합니다.")
    private final String password;

    public UserLoginRequestDto() {
        this(null, null);
    }

    public UserLoginRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public User toEntity() {
        return new User(null, userId, password, null, null);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("UserLoginRequestDto={userId=%s, password=%s}", userId, password);
    }
}
