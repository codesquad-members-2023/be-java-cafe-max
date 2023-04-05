package kr.codesqaud.cafe.web.exception.user;

import kr.codesqaud.cafe.web.exception.BaseException;
import kr.codesqaud.cafe.web.exception.BaseExceptionType;

public class UserNotLoginMatchingException extends BaseException {

    public UserNotLoginMatchingException(
        BaseExceptionType exceptionType) {
        super(exceptionType);
    }
}
