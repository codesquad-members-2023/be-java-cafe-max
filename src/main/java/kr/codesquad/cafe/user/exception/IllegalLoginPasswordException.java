package kr.codesquad.cafe.user.exception;

public class IllegalLoginPasswordException extends RuntimeException {
    private static final String MESSAGE = "이메일이 존재하지 않습니다";

    public IllegalLoginPasswordException() {
        super(MESSAGE);
    }
}
