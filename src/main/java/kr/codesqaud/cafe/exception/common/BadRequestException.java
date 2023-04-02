package kr.codesqaud.cafe.exception.common;

public class BadRequestException extends RuntimeException {

    private final BadRequestExceptionCode badRequestExceptionCode;
    private final String errorCode;
    private final Object errorData;

    public BadRequestException(Object errorData) {
        badRequestExceptionCode = BadRequestExceptionCode
            .getBadException(this.getClass());
        this.errorCode = badRequestExceptionCode.getErrorCode();
        this.errorData = errorData;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return badRequestExceptionCode.getErrorMessage();
    }

    public Object getErrorData() {
        return errorData;
    }
}
