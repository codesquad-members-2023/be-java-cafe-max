package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum UserExceptionType implements BaseExceptionType {
    ALREADY_EXIST_USERID(600, HttpStatus.OK, "이미 존재하는 아이디입니다."),
    ALREADY_EXIST_EMAIL(601, HttpStatus.OK, "이미 존재하는 이메일입니다."),
    INVALID_USER_FORMAT(700, HttpStatus.OK, "올바르지 않은 입력 형식입니다."),
    NOT_FOUND_USER(800, HttpStatus.OK, "회원을 찾을 수 없습니다.");

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    UserExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
