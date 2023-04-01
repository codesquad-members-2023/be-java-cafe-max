package kr.codesqaud.cafe.exception;

public class IDNotFoundException extends RuntimeException{
    public IDNotFoundException(String message) {
        super(message);
    }
}
