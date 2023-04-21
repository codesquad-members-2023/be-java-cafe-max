package kr.codesqaud.cafe.exception;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException() {
        super("댓글을 수정할 권한이 없습니다.");
    }
}
