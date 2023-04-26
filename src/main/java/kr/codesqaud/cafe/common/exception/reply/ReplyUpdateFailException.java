package kr.codesqaud.cafe.common.exception.reply;

public class ReplyUpdateFailException extends RuntimeException {
    private static final String ERROR_MESSAGE = "댓글 수정 권한이 없습니다.";

    public ReplyUpdateFailException() {
        super(ERROR_MESSAGE);
    }
}
