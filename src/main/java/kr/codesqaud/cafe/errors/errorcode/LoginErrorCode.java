package kr.codesqaud.cafe.errors.errorcode;

import org.springframework.http.HttpStatus;

public enum LoginErrorCode implements ErrorCode {

    NOT_MATCH_LOGIN(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인 상태가 아닙니다.");

    private HttpStatus httpStatus;
    private String message;

    LoginErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }


    @Override
    public String getName() {
        return name();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
