package kr.codesqaud.cafe.user.exception;

public class IllegalEditPasswordException extends RuntimeException {
    private static final String MESSAGE = "비밀번호가 일치하지 않습니다";

    public IllegalEditPasswordException() {
        super(MESSAGE);
    }
}
