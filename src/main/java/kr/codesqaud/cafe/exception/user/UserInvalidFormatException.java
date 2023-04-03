package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.exception.BaseException;
import kr.codesqaud.cafe.exception.BaseExceptionType;

public class UserInvalidFormatException extends BaseException {

    public UserInvalidFormatException(BaseExceptionType exceptionType) {
        super(exceptionType);
    }
}
