package kr.codesqaud.cafe.common.exception;

public class CommonException extends RuntimeException {
    private final CommonExceptionType exceptionType;

    public CommonException(CommonExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public CommonExceptionType getExceptionType() {
        return exceptionType;
    }
}