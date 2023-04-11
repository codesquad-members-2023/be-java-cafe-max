package kr.codesqaud.cafe.exception;

public class UserUpdateInvalidPasswordException extends RuntimeException{
    private String id;
    private static final String USER_UPDATE_INVALID_PASSWORD_EXCEPTION = "비밀번호가 일치하지 않습니다.";
    public UserUpdateInvalidPasswordException(String id) {
        super(USER_UPDATE_INVALID_PASSWORD_EXCEPTION);
        this.id = id;
    }
    public String getId() {
        return id;
    }
}
