package kr.codesqaud.cafe.web.exception.user;

import kr.codesqaud.cafe.web.exception.BaseException;
import kr.codesqaud.cafe.web.exception.BaseExceptionType;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(BaseExceptionType exceptionType) {
        super(exceptionType);
    }
}
