package kr.codesqaud.cafe.user.controller.response;

public class UserProfileResponse {

    private final String name;
    private final String email;

    public UserProfileResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
