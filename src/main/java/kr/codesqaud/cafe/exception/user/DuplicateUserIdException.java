package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.dto.user.UserSaveRequest;

public class DuplicateUserIdException extends RuntimeException {
    public final UserSaveRequest userSaveRequest;

    public DuplicateUserIdException(final UserSaveRequest userSaveRequest) {
        super("이미 사용 중인 아이디입니다.");
        this.userSaveRequest = userSaveRequest;
    }

    public UserSaveRequest getUserSaveRequest() {
        return userSaveRequest;
    }

}
