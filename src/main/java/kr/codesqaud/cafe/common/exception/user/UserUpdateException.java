package kr.codesqaud.cafe.common.exception.user;

import kr.codesqaud.cafe.controller.dto.user.UserUpdateDto;

public class UserUpdateException extends RuntimeException {
    private final UserExceptionType userExceptionType;
    private final UserUpdateDto userUpdateDto;

    public UserUpdateException(UserExceptionType userExceptionType, UserUpdateDto userUpdateDto) {
        this.userExceptionType = userExceptionType;
        this.userUpdateDto = userUpdateDto;
    }

    public UserExceptionType getUserExceptionType() {
        return userExceptionType;
    }

    public UserUpdateDto getUserUpdateDto() {
        return userUpdateDto;
    }
}
