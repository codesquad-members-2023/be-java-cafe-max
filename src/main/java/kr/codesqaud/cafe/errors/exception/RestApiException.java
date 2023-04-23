package kr.codesqaud.cafe.errors.exception;

import kr.codesqaud.cafe.errors.errorcode.ErrorCode;

public class RestApiException extends RuntimeException {

    private final ErrorCode errorCode;

    public RestApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
