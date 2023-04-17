package kr.codesqaud.cafe.exception.user;

public class UserUpdateInvalidPasswordException extends RuntimeException{
    private static final String USER_UPDATE_INVALID_PASSWORD_EXCEPTION = "비밀번호가 일치하지 않습니다.";
    public UserUpdateInvalidPasswordException() {
        super(USER_UPDATE_INVALID_PASSWORD_EXCEPTION);
    }
}
