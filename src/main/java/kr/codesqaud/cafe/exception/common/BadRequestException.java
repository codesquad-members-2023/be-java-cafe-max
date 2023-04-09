package kr.codesqaud.cafe.exception.common;

public class BadRequestException extends RuntimeException {

    private final String viewName;
    private final Object errorData;
    private final String field;
    private final String errorCode;

    public BadRequestException(String viewName, Object errorData, String field, String errorCode) {
        this.viewName = viewName;
        this.errorData = errorData;
        this.field = field;
        this.errorCode = errorCode;
    }

    public String getViewName() {
        return viewName;
    }

    public Object getErrorData() {
        return errorData;
    }

    public String getField() {
        return field;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
