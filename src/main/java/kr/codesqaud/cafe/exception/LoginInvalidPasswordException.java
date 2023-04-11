package kr.codesqaud.cafe.exception;

public class LoginInvalidPasswordException extends RuntimeException{
    private static final String LOGIN_INVALID_PASSWORD_EXCEPTION = "잘못된 비밀번호 입니다.";
    public LoginInvalidPasswordException(){
        super(LOGIN_INVALID_PASSWORD_EXCEPTION);
    }
}
