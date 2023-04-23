package kr.codesqaud.cafe.exception.common;

public class CommonException extends RuntimeException {
    private final CommonExceptionType commonExceptionType;

    public CommonException(CommonExceptionType commonExceptionType) {
        this.commonExceptionType = commonExceptionType;
    }

    public CommonExceptionType getCommonExceptionType() {
        return commonExceptionType;
    }
}
