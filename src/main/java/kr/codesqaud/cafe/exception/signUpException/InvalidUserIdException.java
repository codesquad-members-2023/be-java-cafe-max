package kr.codesqaud.cafe.exception.signUpException;

public class InvalidUserIdException extends IllegalSignUpStateException {

    public static final String ERROR_MESSAGE = "ID가 이미 존재합니다.";

    public InvalidUserIdException() {
        super(ERROR_MESSAGE);
    }

}
