package kr.codesqaud.cafe.post.exception;

public class IllegalPostEditAccessException extends RuntimeException {
    private static final String MESSAGE = "접근 불가능한 페이지입니다.";
    public IllegalPostEditAccessException() {
        super(MESSAGE);
    }
}
