package kr.codesquad.cafe.global.exception;

public class InsufficientPermissionException extends RuntimeException {
    private static final String MESSAGE = "접근 할 권한이 없습니다.";

    public InsufficientPermissionException() {
        super(MESSAGE);
    }
}
