package kr.codesqaud.cafe.common.exception.user;

public class UserLoginException extends RuntimeException {
    private final UserExceptionType userExceptionType;

    public UserLoginException(UserExceptionType userExceptionType) {
        this.userExceptionType = userExceptionType;
    }

    public UserExceptionType getUserExceptionType() {
        return userExceptionType;
    }
}
