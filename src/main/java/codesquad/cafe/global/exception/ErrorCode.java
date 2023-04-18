package codesquad.cafe.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // == user exception == //
    ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "U0001", "이미 존재하는 회원입니다.", "error/400"),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "U0002", "존재하지 않는 회원입니다.", "error/400"),
    WRONG_INPUT_PASSWORD(HttpStatus.BAD_REQUEST, "U0003", "비밀번호가 일치하지 않습니다.", "error/400"),
    NOT_FOUND_SESSION(HttpStatus.BAD_REQUEST, "U0004", "로그인이 필요한 기능입니다.","error/401"),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "U0005", "인증되지 않은 사용자입니다.", "error/401"),
    NOT_MATCH_USER_AND_WRITER(HttpStatus.FORBIDDEN, "U0006", "다른 사람의 글을 수정할 수 없다.", "error/401");

    private final HttpStatus status;
    private final String code;
    private final String message;
    private final String viewName;

    ErrorCode(final HttpStatus status, final String code, final String message, final String viewName) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.viewName = viewName;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getViewName() {
        return viewName;
    }
}
