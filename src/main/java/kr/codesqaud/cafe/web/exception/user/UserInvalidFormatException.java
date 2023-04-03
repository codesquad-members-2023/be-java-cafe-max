package kr.codesqaud.cafe.web.exception.user;

import kr.codesqaud.cafe.web.exception.BaseException;
import kr.codesqaud.cafe.web.exception.BaseExceptionType;

public class UserInvalidFormatException extends BaseException {

    public UserInvalidFormatException(BaseExceptionType exceptionType) {
        super(exceptionType);
    }
}
