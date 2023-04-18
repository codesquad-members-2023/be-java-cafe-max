package kr.codesqaud.cafe.exception;

public class ReplyNotFoundException extends RuntimeException {

    public ReplyNotFoundException() {
        super("존재하지 않는 댓글입니다.");
    }

}
