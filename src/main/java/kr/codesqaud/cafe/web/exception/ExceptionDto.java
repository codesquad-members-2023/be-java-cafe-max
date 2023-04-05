package kr.codesqaud.cafe.web.exception;

import org.springframework.http.HttpStatus;

public class ExceptionDto {

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    public ExceptionDto(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return String.format("{errorCode: %d, httpStatus: %s, errorMessage: %s}",
            errorCode, httpStatus, errorMessage);
    }
}
