package kr.codesqaud.cafe.exception.member;

import kr.codesqaud.cafe.exception.common.BadRequestException;

public class DuplicateMemberEmailException extends BadRequestException {

    public DuplicateMemberEmailException(String viewName, Object errorData) {
        super(viewName, errorData, "email", "Duplicate");
    }
}
