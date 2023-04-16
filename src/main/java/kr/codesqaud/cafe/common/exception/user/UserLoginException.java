package kr.codesqaud.cafe.common.exception.user;

import kr.codesqaud.cafe.controller.dto.user.UserLoginDto;

public class UserLoginException extends RuntimeException {
    private final UserExceptionType userExceptionType;
    private final UserLoginDto userLoginDto;

    public UserLoginException(UserExceptionType userExceptionType, UserLoginDto userLoginDto) {
        this.userExceptionType = userExceptionType;
        this.userLoginDto = userLoginDto;
    }

    public UserExceptionType getUserExceptionType() {
        return userExceptionType;
    }

    public UserLoginDto getUserLoginDto() {
        return userLoginDto;
    }
}
