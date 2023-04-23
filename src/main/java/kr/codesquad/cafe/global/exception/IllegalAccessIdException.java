package kr.codesquad.cafe.global.exception;

public class IllegalAccessIdException extends RuntimeException {
    private static final String MESSAGE = "접근 할 수 없습니다.";

    public IllegalAccessIdException() {
        super(MESSAGE);
    }
}
