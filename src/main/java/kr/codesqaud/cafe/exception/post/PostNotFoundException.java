package kr.codesqaud.cafe.exception.post;

import kr.codesqaud.cafe.exception.common.NotFoundException;

public class PostNotFoundException extends NotFoundException {

    private static final String ERROR_MASSAGE = "해당 게시물을 찾을 수 없습니다.";

    public PostNotFoundException() {
        super(ERROR_MASSAGE);
    }
}
