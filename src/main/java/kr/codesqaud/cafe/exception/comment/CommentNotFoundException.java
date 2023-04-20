package kr.codesqaud.cafe.exception.comment;

import kr.codesqaud.cafe.exception.common.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends ApiException {

    private static final String ERROR_MESSAGE = "해당 댓글을 찾을 수 없습니다.";

    public CommentNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), ERROR_MESSAGE);
    }
}
