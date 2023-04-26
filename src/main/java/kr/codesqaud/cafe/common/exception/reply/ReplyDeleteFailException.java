package kr.codesqaud.cafe.common.exception.reply;

public class ReplyDeleteFailException extends RuntimeException{
    private static final String ERROR_MESSAGE = "댓글 삭제 권한이 없습니다.";

    public ReplyDeleteFailException() {
        super(ERROR_MESSAGE);
    }
}