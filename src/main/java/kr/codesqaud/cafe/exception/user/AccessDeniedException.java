package kr.codesqaud.cafe.exception.user;

public class AccessDeniedException extends RuntimeException {
    private static final String MESSAGE = "접근 권한이 없습니다.";

    public AccessDeniedException() {
        super(MESSAGE);
    }

}
