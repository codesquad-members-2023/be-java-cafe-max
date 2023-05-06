package kr.codesquad.cafe.global.exception;

public class UnauthorizedAccessException extends RuntimeException {
    private static final String MESSAGE = "로그인하지 않아서 접근 할 수 없습니다.";

    public UnauthorizedAccessException() {
        super(MESSAGE);
    }
}
