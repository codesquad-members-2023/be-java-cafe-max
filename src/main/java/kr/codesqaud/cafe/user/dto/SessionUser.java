package kr.codesqaud.cafe.user.dto;

import kr.codesqaud.cafe.user.domain.User;

public class SessionUser {
    private final String userId;
    private final String userName;

    public SessionUser(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public static SessionUser from(User user) {
        return new SessionUser(user.getUserId(), user.getUserName());
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

}
