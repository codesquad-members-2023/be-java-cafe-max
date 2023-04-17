package codesquad.cafe.global.exception;

import codesquad.cafe.domain.user.exception.UserErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

public class ErrorResponse {
    private final HttpStatus status;
    private final String code;
    private final String message;
    private final String viewName;

    public ErrorResponse(CustomException e) {
        UserErrorCode errorCode = e.getErrorCode();
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
