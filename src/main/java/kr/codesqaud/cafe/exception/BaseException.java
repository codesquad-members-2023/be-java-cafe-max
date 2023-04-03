package kr.codesqaud.cafe.exception;

public abstract class BaseException extends RuntimeException {

    private final BaseExceptionType exceptionType;

    public BaseException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public BaseExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
