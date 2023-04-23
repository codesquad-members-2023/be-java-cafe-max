package kr.codesquad.cafe.user.exception;

public class DuplicateEmailException extends RuntimeException {
    private static final String MESSAGE = "이미 존재하는 이메일입니다";

    public DuplicateEmailException() {
        super(MESSAGE);
    }
}
