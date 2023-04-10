package kr.codesqaud.cafe.exception;

public class UserNotFoundException extends RuntimeException{

    private static final String USER_NOT_FOUND_EXCEPTION = "해당 사용자가 없습니다.";
    public UserNotFoundException(){
        super(USER_NOT_FOUND_EXCEPTION);
    }
}
