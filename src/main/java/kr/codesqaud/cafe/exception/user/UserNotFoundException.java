package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.exception.common.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    private static final String ERROR_MESSAGE = "회원을 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(ERROR_MESSAGE);
    }
}