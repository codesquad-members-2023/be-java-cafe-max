package kr.codesqaud.cafe.exception;

public class AlreadyUserExistenceException extends RuntimeException{

    private static final String ID_DUPLICATED_EXCEPTION = "중복된 아이디가 존재합니다.";
    public AlreadyUserExistenceException() {
        super(ID_DUPLICATED_EXCEPTION);
    }
}

