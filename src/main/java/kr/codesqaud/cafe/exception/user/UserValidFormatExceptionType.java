package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

/**
 * 사용자의 입력 형식이 유효하지 않을때의 타입을 저장한 객체
 */
public enum UserValidFormatExceptionType implements BaseExceptionType {
    INVALID_USER_FORMAT(700, HttpStatus.OK, "올바르지 않은 입력 형식입니다.");

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    UserValidFormatExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
