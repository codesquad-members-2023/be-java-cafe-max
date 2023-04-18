package kr.codesqaud.cafe.user.exception;

public class NoSuchLoginEmailException extends RuntimeException {
    private static final String MESSAGE = "이메일이 존재하지 않습니다";

    public NoSuchLoginEmailException() {
        super(MESSAGE);
    }

}
