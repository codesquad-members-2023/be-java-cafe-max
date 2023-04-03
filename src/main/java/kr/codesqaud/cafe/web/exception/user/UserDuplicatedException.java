package kr.codesqaud.cafe.web.exception.user;

import kr.codesqaud.cafe.web.exception.BaseException;
import kr.codesqaud.cafe.web.exception.BaseExceptionType;

public class UserDuplicatedException extends BaseException {

    public UserDuplicatedException(BaseExceptionType exceptionType) {
        super(exceptionType);
    }
}
