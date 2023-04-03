package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.exception.BaseException;
import kr.codesqaud.cafe.exception.BaseExceptionType;

public class UserDuplicatedException extends BaseException {

    public UserDuplicatedException(BaseExceptionType exceptionType) {
        super(exceptionType);
    }
}
