package kr.codesqaud.cafe.exception.common;

public class ErrorCodeNotFoundException extends BadRequestException {

    public ErrorCodeNotFoundException(Object errorValue) {
        super(errorValue);
    }
}
