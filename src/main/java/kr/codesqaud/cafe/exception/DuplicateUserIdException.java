package kr.codesqaud.cafe.exception;

public class DuplicateUserIdException extends DuplicateKeyException {
    public DuplicateUserIdException(String message) {
        super(message);
    }
}
