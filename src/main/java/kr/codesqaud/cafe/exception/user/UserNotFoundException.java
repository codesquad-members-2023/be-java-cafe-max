package kr.codesqaud.cafe.exception.user;

public class UserNotFoundException extends RuntimeException{

    private static final String USER_NOT_FOUND_EXCEPTION = "존재하지 않는 아이디 입니다.";
    public UserNotFoundException(){
        super(USER_NOT_FOUND_EXCEPTION);
    }
}
