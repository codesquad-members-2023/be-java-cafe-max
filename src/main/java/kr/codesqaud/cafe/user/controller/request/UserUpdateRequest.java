package kr.codesqaud.cafe.user.controller.request;

import kr.codesqaud.cafe.user.service.User;

public class UserUpdateRequest {

    private final String userId;
    private final String currPassword;
    private final String newPassword;
    private final String name;
    private final String email;

    public UserUpdateRequest(String userId, String currPassword, String newPassword, String name, String email) {
        this.userId = userId;
        this.currPassword = currPassword;
        this.newPassword = newPassword;
        this.name = name;
        this.email = email;
    }

    public String getCurrPassword() {
        return currPassword;
    }

    public User toUser() {
        return new User(null, userId, newPassword, name, email);
    }
}
