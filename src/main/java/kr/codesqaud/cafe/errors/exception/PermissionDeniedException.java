package kr.codesqaud.cafe.errors.exception;

import kr.codesqaud.cafe.errors.errorcode.ErrorCode;

public class PermissionDeniedException extends RuntimeException {

    private final ErrorCode errorCode;

    public PermissionDeniedException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
