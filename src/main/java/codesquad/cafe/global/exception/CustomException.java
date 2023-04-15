package codesquad.cafe.global.exception;


public class CustomException extends RuntimeException {
    private ErrorCode errorCode;

    public CustomException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
