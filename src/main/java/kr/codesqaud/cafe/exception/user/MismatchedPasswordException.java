package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.dto.user.UserUpdateRequest;

public class MismatchedPasswordException extends RuntimeException {
    private final UserUpdateRequest userUpdateRequest;
    public MismatchedPasswordException(final UserUpdateRequest userUpdateRequest) {
        super("비밀번호가 일치하지 않습니다.");
        this.userUpdateRequest = userUpdateRequest;
    }

    public UserUpdateRequest getUserUpdateRequest() { return userUpdateRequest; }
}
