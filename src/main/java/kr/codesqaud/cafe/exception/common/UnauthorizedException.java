package kr.codesqaud.cafe.exception.common;

public class UnauthorizedException extends RuntimeException{

    private static final String ERROR_MESSAGE = "로그인이 필요한 기능입니다.";

    public UnauthorizedException() {
        super(ERROR_MESSAGE);
    }

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
