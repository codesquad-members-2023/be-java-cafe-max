package kr.codesqaud.cafe.dto.error;

public class ErrorResponse {

    private final Integer status;
    private final String message;

    public ErrorResponse(Integer status, String message) {
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
