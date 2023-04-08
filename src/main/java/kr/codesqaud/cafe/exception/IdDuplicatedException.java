package kr.codesqaud.cafe.exception;

public class IdDuplicatedException extends RuntimeException{

    private static final String ID_DUPLICATE_EXCEPTION = "중복된 아이디가 존재합니다.";
    public IdDuplicatedException() {
        super(ID_DUPLICATE_EXCEPTION);
    }
}

