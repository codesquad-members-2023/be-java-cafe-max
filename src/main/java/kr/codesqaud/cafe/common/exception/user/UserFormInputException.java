package kr.codesqaud.cafe.common.exception.user;

import kr.codesqaud.cafe.controller.dto.user.UserFormDto;

public class UserFormInputException extends RuntimeException {
    private final UserExceptionType userExceptionType;
    private final UserFormDto userFormDto;

    public UserFormInputException(UserExceptionType userExceptionType, UserFormDto userFormDto) {
        this.userExceptionType = userExceptionType;
        this.userFormDto = userFormDto;
    }

    public UserExceptionType getUserExceptionType() {
        return userExceptionType;
    }

    public UserFormDto getUserFormDto() {
        return userFormDto;
    }
}
