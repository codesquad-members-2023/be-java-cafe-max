package kr.codesqaud.cafe.user.controller.response;

import kr.codesqaud.cafe.user.service.User;

public class UserProfileResponse {

    private final String name;
    private final String email;

    private UserProfileResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static UserProfileResponse from(final User user) {
        return new UserProfileResponse(user.getName(), user.getEmail());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
