package kr.codesqaud.cafe.exception.user;

public class UserNotFoundException extends RuntimeException {

    public static final String ERROR_MESSAGE = "존재하지 않는 ID입니다.";

    public UserNotFoundException() {
        super(ERROR_MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
