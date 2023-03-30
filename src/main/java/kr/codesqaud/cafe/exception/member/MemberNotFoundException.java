package kr.codesqaud.cafe.exception.member;

import kr.codesqaud.cafe.exception.common.BadRequestException;

public class MemberNotFoundException extends BadRequestException {

    public MemberNotFoundException(Object errorValue) {
        super(errorValue);
    }
}
