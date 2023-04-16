package kr.codesqaud.cafe.exception.common;

public class Unauthorized extends RuntimeException {

    private static final String ERROR_MASSAGE = "권환이 없습니다.";

    public Unauthorized() {
        super(ERROR_MASSAGE);
    }

    public Unauthorized(String message) {
        super(message);
    }
}
