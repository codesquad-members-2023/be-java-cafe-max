package kr.codesquad.cafe.user.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "이메일이 존재하지 않습니다";

    public UserNotFoundException() {
        super(MESSAGE);
    }

}
