package kr.codesqaud.cafe.app.user.controller.dto;

import javax.validation.constraints.Pattern;
import kr.codesqaud.cafe.app.user.entity.User;

public class UserSavedRequest {

    @Pattern(regexp = "^[a-z\\d_-]{5,20}$", message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
    private final String userId;
    @Pattern(regexp = "[A-Za-z\\d!@#$%^&*()_+]{8,16}$", message = "8~16자 영문 대 소문자, 숫자, 특수문자만 사용 가능합니다.")
    private final String password;
    @Pattern(regexp = "^[a-z가-힣]{1,20}$", message = "1~20자 영문 소문자, 한글만 사용 가능합니다.")
    private final String name;
    @Pattern(regexp = "[a-z\\d]+@[a-z]+\\.[a-z]{2,3}", message = "(소문자 또는 숫자로 최소1글자)@(소문자 최소1글자).(소문자 2~3글자) 형식으로 사용 가능합니다.")
    private final String email;

    public UserSavedRequest() {
        this(null, null, null, null);
    }

    public UserSavedRequest(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        return toEntity(null);
    }

    public User toEntity(Long id) {
        return new User(id, userId, password, name, email);
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

    @Override
    public String toString() {
        return String.format("UserSavedRequestDto{userId=%s, password=%s, name=%s, email=%s}",
            userId, password, name, email);
    }
}
