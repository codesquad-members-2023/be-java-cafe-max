package kr.codesqaud.cafe.exception;

public class PasswordNotFoundException extends RuntimeException{
    public PasswordNotFoundException(String message) {
        super(message);
    }
}
