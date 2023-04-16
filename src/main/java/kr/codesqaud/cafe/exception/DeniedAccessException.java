package kr.codesqaud.cafe.exception;

public class DeniedAccessException extends RuntimeException{
    public DeniedAccessException(String message) {
        super(message);
    }
}
