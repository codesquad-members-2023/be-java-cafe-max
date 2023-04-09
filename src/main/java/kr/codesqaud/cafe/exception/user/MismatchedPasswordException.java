package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.dto.user.UserUpdateRequest;

public class MismatchedPasswordException extends RuntimeException {
    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";
    private final UserUpdateRequest userUpdateRequest;
    public MismatchedPasswordException(final UserUpdateRequest userUpdateRequest) {
        super(MESSAGE);
        this.userUpdateRequest = userUpdateRequest;
    }

    public UserUpdateRequest getUserUpdateRequest() { return userUpdateRequest; }
}
