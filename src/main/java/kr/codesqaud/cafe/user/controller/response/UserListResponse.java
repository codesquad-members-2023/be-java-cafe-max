package kr.codesqaud.cafe.user.controller.response;

public class UserListResponse {

    private final long index;
    private final String userId;
    private final String name;
    private final String email;

    public UserListResponse(long index, String userId, String name, String email) {
        this.index = index;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
