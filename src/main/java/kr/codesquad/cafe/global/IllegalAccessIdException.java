package kr.codesquad.cafe.global;

public class IllegalAccessIdException extends RuntimeException {
    private static String MESSAGE = "접근 할 수 없습니다.";

    public IllegalAccessIdException() {
        super(MESSAGE);
    }
}
