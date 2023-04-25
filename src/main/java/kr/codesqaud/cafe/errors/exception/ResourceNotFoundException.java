package kr.codesqaud.cafe.errors.exception;

import kr.codesqaud.cafe.errors.errorcode.ErrorCode;

public class ResourceNotFoundException extends RuntimeException{

    private final ErrorCode errorCode;

    public ResourceNotFoundException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "ResourceNotFoundException{" +
            "errorCode=" + errorCode +
            '}';
    }
}
