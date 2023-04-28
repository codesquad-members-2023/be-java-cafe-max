package codesquad.cafe.global.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private final HttpStatus status;
    private final String code;
    private final String message;
    private final String viewName;

    public ErrorResponse(final ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.viewName = errorCode.getViewName();
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
