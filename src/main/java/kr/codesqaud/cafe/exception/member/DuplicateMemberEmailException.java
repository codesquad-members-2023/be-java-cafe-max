package kr.codesqaud.cafe.exception.member;

import kr.codesqaud.cafe.exception.common.BadRequestException;

public class DuplicateMemberEmailException extends BadRequestException {

    public DuplicateMemberEmailException(Object errorValue) {
        super(errorValue);
    }
}
