package kr.codesqaud.cafe.web.exception.user;

import kr.codesqaud.cafe.web.exception.BaseException;
import kr.codesqaud.cafe.web.exception.BaseExceptionType;

public class UserNotPasswordMatchingException extends BaseException {

    public UserNotPasswordMatchingException(
        BaseExceptionType exceptionType) {
        super(exceptionType);
    }
}
