package kr.codesqaud.cafe.exception;

public class InvalidPasswordException extends RuntimeException{
    private String id;
    public InvalidPasswordException(String id) {
        super("비밀번호가 일치하지 않습니다.");
        this.id = id;
    }
    public String getId() {
        return id;
    }
}
