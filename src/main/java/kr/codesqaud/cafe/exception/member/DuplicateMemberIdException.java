package kr.codesqaud.cafe.exception.member;

import kr.codesqaud.cafe.exception.common.BadRequestException;

public class DuplicateMemberIdException extends BadRequestException {

    public DuplicateMemberIdException(Object errorData) {
        super(errorData);
    }
}
