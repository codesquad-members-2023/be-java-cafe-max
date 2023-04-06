package kr.codesqaud.cafe.exception.signUpException;

/**
 * 회원가입 관련 예외를 담당하는 클래스
 */
public class IllegalSignUpStateException extends RuntimeException {

    public IllegalSignUpStateException() {
    }

    public IllegalSignUpStateException(String message) {
        super(message);
    }

    public IllegalSignUpStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalSignUpStateException(Throwable cause) {
        super(cause);
    }
}
