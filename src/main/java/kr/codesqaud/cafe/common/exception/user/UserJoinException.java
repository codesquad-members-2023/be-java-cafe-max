package kr.codesqaud.cafe.common.exception.user;

import kr.codesqaud.cafe.controller.dto.user.UserJoinDto;

public class UserJoinException extends RuntimeException {
    private final UserExceptionType userExceptionType;
    private final UserJoinDto userJoinDto;

    public UserJoinException(UserExceptionType userExceptionType, UserJoinDto userJoinDto) {
        this.userExceptionType = userExceptionType;
        this.userJoinDto = userJoinDto;
    }

    public UserExceptionType getUserExceptionType() {
        return userExceptionType;
    }

    public UserJoinDto getUserJoinDto() {
        return userJoinDto;
    }
}
