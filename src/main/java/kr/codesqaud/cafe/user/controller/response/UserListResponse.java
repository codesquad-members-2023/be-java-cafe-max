package kr.codesqaud.cafe.user.controller.response;

import kr.codesqaud.cafe.user.service.User;

public class UserListResponse {

    private final long index;
    private final String userId;
    private final String name;
    private final String email;

    private UserListResponse(long index, String userId, String name, String email) {
        this.index = index;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public static UserListResponse from(final User user, long index) {
        return new UserListResponse(index, user.getUserId(), user.getName(), user.getEmail());
    }
}
