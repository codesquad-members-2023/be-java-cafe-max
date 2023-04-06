package kr.codesqaud.cafe.exception;

public class IdDuplicatedException extends RuntimeException{
    public IdDuplicatedException() {
        super("중복된 아이디가 존재합니다.");
    }
}

