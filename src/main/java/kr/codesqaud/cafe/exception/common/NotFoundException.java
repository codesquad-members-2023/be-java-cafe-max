package kr.codesqaud.cafe.exception.common;

public class NotFoundException extends RuntimeException {

    private final String errorMessage;

    public NotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
