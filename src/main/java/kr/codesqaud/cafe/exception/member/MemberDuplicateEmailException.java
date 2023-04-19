package kr.codesqaud.cafe.exception.member;

import kr.codesqaud.cafe.exception.common.BadRequestException;

public class MemberDuplicateEmailException extends BadRequestException {

    public MemberDuplicateEmailException(Object errorData) {
        super(errorData, "email", "Duplicate");
    }
}
