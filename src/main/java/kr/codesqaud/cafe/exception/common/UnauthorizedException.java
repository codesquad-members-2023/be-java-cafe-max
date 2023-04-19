package kr.codesqaud.cafe.exception.common;

public class UnauthorizedException extends RuntimeException {

    private static final String ERROR_MESSAGE = "권환이 없습니다.";

    public UnauthorizedException() {
        super(ERROR_MESSAGE);
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
