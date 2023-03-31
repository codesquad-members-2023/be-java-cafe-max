package kr.codesqaud.cafe.exception.post;

import kr.codesqaud.cafe.exception.common.BadRequestException;

public class PostNotFoundException extends BadRequestException {

    public PostNotFoundException(Object errorValue) {
        super(errorValue);
    }
}
