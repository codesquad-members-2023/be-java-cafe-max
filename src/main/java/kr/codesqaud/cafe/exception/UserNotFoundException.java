package kr.codesqaud.cafe.exception;

public class UserNotFoundException extends RuntimeException{

    private static final String USER_NOT_FOUND_EXCEPTION = "비밀번호가 일치하지 않습니다.";
    public UserNotFoundException(){
        super(USER_NOT_FOUND_EXCEPTION);
    }
}
