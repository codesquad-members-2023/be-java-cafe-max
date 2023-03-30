package kr.codesqaud.cafe.exception;

public class DuplicateMemberIdException extends RuntimeException {

    public static final String MESSAGE = "회원 아이디가 중복입니다.";

    public DuplicateMemberIdException() {
        super(MESSAGE);
    }

    public DuplicateMemberIdException(String message) {
        super(message);
    }

    public DuplicateMemberIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
