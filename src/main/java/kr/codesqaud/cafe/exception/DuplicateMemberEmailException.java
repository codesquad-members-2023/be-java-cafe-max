package kr.codesqaud.cafe.exception;

public class DuplicateMemberEmailException extends RuntimeException {

    public static final String MESSAGE = "회원 이메일이 중복입니다.";

    public DuplicateMemberEmailException() {
        super(MESSAGE);
    }

    public DuplicateMemberEmailException(String message) {
        super(message);
    }

    public DuplicateMemberEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
