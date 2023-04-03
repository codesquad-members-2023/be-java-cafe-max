package kr.codesqaud.cafe.exception.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("존재하지 않는 회원입니다.");
    }
}
