package codesquad.cafe.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // == user exception == //
    ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "U0001", "이미 존재하는 회원입니다.", "user/form"),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "U0002", "존재하지 않는 회원입니다.", "user/login"),
    WRONG_INPUT_PASSWORD(HttpStatus.BAD_REQUEST, "U0003", "비밀번호가 일치하지 않습니다.", "user/user/updateForm");

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
