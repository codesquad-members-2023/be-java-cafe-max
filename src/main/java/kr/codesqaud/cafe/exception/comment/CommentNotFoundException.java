package kr.codesqaud.cafe.exception.comment;

import kr.codesqaud.cafe.exception.common.NotFoundException;

public class CommentNotFoundException extends NotFoundException {

    private static final String ERROR_MASSAGE = "해당 댓글을 찾을 수 없습니다.";

    public CommentNotFoundException() {
        super(ERROR_MASSAGE);
    }
}
