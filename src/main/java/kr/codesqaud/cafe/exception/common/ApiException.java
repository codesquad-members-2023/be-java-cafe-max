package kr.codesqaud.cafe.exception.common;

public class ApiException extends RuntimeException {

    private final Integer status;
    private final String message;

    public ApiException(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
