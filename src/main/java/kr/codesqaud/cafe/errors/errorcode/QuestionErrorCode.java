package kr.codesqaud.cafe.errors.errorcode;

import org.springframework.http.HttpStatus;

public enum QuestionErrorCode implements ErrorCode {
    NOT_FOUND_QUESTION(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    QuestionErrorCode(HttpStatus httpStatus, String message) {
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
