package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum UserNotFoundExceptionType implements BaseExceptionType {
    NOT_FOUND_USER(800, HttpStatus.OK, "회원을 찾을 수 없습니다.");

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    UserNotFoundExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
