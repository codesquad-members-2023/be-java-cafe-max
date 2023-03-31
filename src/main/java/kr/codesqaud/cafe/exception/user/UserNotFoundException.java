package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.exception.BaseException;
import kr.codesqaud.cafe.exception.BaseExceptionType;

public class UserNotFoundException extends BaseException {

    private final BaseExceptionType exceptionType;

    public UserNotFoundException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
