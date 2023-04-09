package kr.codesqaud.cafe.exception.member;

import kr.codesqaud.cafe.exception.common.BadRequestException;

public class NotMatchMemberPassword extends BadRequestException {

    public NotMatchMemberPassword(String viewName, Object errorData) {
        super(viewName, errorData, "password", "NotMatch");
    }
}
