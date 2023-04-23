package kr.codesqaud.cafe.exception.common;

import org.springframework.http.HttpStatus;

public enum CommonExceptionType {
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없습니다.");
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
