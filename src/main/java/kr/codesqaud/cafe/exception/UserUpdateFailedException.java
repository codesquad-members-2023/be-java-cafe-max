package kr.codesqaud.cafe.exception;

public class UserUpdateFailedException extends RuntimeException {
    private String errorType;

    public UserUpdateFailedException(String message, String errorType) {
        super(message);
        this.errorType = errorType;
    }

    public String getErrorType() {
        return errorType;
    }
}
