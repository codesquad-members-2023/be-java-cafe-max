package kr.codesquad.cafe.user.exception;

public class IncorrectPasswordException extends RuntimeException {
    private static final String MESSAGE = "이메일이 존재하지 않습니다";

    public IncorrectPasswordException() {
        super(MESSAGE);
    }
}
