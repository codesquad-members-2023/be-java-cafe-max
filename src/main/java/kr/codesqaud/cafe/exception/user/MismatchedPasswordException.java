package kr.codesqaud.cafe.exception.user;

public class MismatchedPasswordException extends RuntimeException {
    public MismatchedPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
