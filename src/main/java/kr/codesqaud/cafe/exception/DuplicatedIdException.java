package kr.codesqaud.cafe.exception;

public class DuplicatedIdException extends IllegalArgumentException{
    public DuplicatedIdException(String message) {
        super(message);
    }
}
