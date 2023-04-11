package kr.codesqaud.cafe.exception.user;

public class LoginFailedException extends RuntimeException {
    private static final String MESSAGE = "일치하는 회원 정보가 없습니다.";

    public LoginFailedException() {
        super(MESSAGE);
    }

}
