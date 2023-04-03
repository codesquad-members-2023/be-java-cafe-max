package kr.codesqaud.cafe.exception.user;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException() {
        super("이미 사용 중인 아이디입니다.");
    }
}
