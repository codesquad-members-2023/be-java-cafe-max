package kr.codesqaud.cafe.exception.common;

import org.springframework.http.HttpStatus;

public enum CommonExceptionType {
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 권한이 없습니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "해당 멤버를 찾을 수 없습니다."),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "해당 이메일을 찾을 수 없습니다."),
    NOT_FOUND_NICKNAME(HttpStatus.NOT_FOUND, "해당 닉네임을 찾을 수 없습니다.");
    private final HttpStatus status;
    private final String errorMessage;

    CommonExceptionType(HttpStatus status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public int getStatusValue() {
        return status.value();
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
