package kr.codesqaud.cafe.errors.errorcode;

import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {
    ALREADY_EXIST_USERID(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다.", null),
    ALREADY_EXIST_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.", null),
    NOT_FOUND_USER(HttpStatus.MOVED_PERMANENTLY, "회원을 찾을 수 없습니다.", "/users"),
    NOT_MATCH_LOGIN(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다.", null),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.", null),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.", null);

    private final HttpStatus httpStatus;
    private final String message;
    private final String redirectUrl;

    UserErrorCode(HttpStatus httpStatus, String message, String redirectUrl) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.redirectUrl = redirectUrl;
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

    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public String toString() {
        return "UserErrorCode{" +
            "httpStatus=" + httpStatus +
            ", message='" + message + '\'' +
            ", redirectUrl='" + redirectUrl + '\'' +
            '}';
    }
}
