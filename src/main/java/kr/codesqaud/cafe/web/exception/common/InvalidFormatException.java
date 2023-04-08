package kr.codesqaud.cafe.web.exception.common;

import kr.codesqaud.cafe.web.exception.BaseException;
import kr.codesqaud.cafe.web.exception.BaseExceptionType;

public class InvalidFormatException extends BaseException {

    public InvalidFormatException(BaseExceptionType exceptionType) {
        super(exceptionType);
    }
}
