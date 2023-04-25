package kr.codesqaud.cafe.common.exception.reply;

public class ReplyUpdateFailedException extends RuntimeException {
    private static final String ERROR_MESSAGE = "댓글 수정에 실패했습니다.";

    public ReplyUpdateFailedException() {
        super(ERROR_MESSAGE);
    }
}
