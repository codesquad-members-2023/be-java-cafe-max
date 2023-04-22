package kr.codesqaud.cafe.exception.common;

public class BadRequestException extends RuntimeException {

    private final BadRequestExceptionView badRequestExceptionView;
    private final Object errorData;
    private final String field;
    private final String errorCode;

    public BadRequestException(Object errorData, String field, String errorCode) {
        this.badRequestExceptionView = BadRequestExceptionView.getBadRequestExceptionView(errorData.getClass());
        this.errorData = errorData;
        this.field = field;
        this.errorCode = errorCode;
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

    public String getViewName() {
        return badRequestExceptionView.getViewName();
    }

    public String getErrorDataClassName() {
        String className = errorData.getClass().getSimpleName();
        return className.replace(className.charAt(0), (char) (className.charAt(0) + 32));
    }
}
