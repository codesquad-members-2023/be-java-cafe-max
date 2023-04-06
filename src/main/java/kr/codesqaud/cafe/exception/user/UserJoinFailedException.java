package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.controller.dto.UserJoinDto;

public class UserJoinFailedException extends RuntimeException {
    private final UserExceptionType userExceptionType;
    private final UserJoinDto userJoinDto;

    public UserJoinFailedException(UserExceptionType userExceptionType, UserJoinDto userJoinDto) {
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
