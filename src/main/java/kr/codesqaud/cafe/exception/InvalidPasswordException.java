package kr.codesqaud.cafe.exception;

public class InvalidPasswordException extends RuntimeException{
    private String id;
    private static final String INVALID_PASSWORD_EXCEPTION = "비밀번호가 일치하지 않습니다.";
    public InvalidPasswordException(String id) {
        super(INVALID_PASSWORD_EXCEPTION);
        this.id = id;
    }
    public String getId() {
        return id;
    }
}
