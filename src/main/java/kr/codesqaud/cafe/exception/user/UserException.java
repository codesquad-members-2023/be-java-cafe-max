package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.exception.BaseException;
import kr.codesqaud.cafe.exception.BaseExceptionType;

public class UserException extends BaseException {

    private final BaseExceptionType exceptionType;

    public UserException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
