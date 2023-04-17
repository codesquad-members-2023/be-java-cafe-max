package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.dto.user.UserSaveRequest;

public class AlreadyNameExistenceException extends RuntimeException {
    private static final String MESSAGE = "이미 사용 중인 닉네임입니다.";
    public final UserSaveRequest userSaveRequest;

    public AlreadyNameExistenceException(final UserSaveRequest userSaveRequest) {
        super(MESSAGE);
        this.userSaveRequest = userSaveRequest;
    }

    public UserSaveRequest getUserSaveRequest() {
        return userSaveRequest;
    }

}
