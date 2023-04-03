package kr.codesqaud.cafe.exception;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException() {
        super("이미 사용 중인 아이디입니다.");
    }
}
