package kr.codesqaud.cafe.exception.member;

import kr.codesqaud.cafe.exception.common.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

    private static final String ERROR_MASSAGE = "해당 회원을 찾을 수 없습니다.";

    public MemberNotFoundException() {
        super(ERROR_MASSAGE);
    }
}
