package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.controller.dto.UserJoinDto;

public class UserJoinFailedException extends RuntimeException {
    private final UserJoinDto userJoinDto;
    private final String errorField;

    public UserJoinFailedException(String message, UserJoinDto userJoinDto, String errorField) {
        super(message);
        this.userJoinDto = userJoinDto;
        this.errorField = errorField;
    }

    public UserJoinDto getUserJoinDto() {
        return userJoinDto;
    }

    public String getErrorField() {
        return errorField;
    }
}
