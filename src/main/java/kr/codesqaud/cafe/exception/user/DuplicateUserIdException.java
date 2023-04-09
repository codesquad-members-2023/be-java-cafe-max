package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.dto.user.UserSaveRequest;

public class DuplicateUserIdException extends RuntimeException {
    private static final String MESSAGE = "이미 사용 중인 아이디입니다.";
    public final UserSaveRequest userSaveRequest;

    public DuplicateUserIdException(final UserSaveRequest userSaveRequest) {
        super(MESSAGE);
        this.userSaveRequest = userSaveRequest;
    }

    public UserSaveRequest getUserSaveRequest() {
        return userSaveRequest;
    }

}
