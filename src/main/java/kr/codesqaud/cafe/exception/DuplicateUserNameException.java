package kr.codesqaud.cafe.exception;

public class DuplicateUserNameException extends DuplicateKeyException {
    public DuplicateUserNameException(String message) {
        super(message);
    }
}
