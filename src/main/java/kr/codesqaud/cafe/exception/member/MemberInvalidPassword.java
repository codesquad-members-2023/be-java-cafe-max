package kr.codesqaud.cafe.exception.member;

import kr.codesqaud.cafe.exception.common.BadRequestException;

public class MemberInvalidPassword extends BadRequestException {

    public MemberInvalidPassword(Object errorData) {
        super(errorData, "password", "Invalid");
    }
}
