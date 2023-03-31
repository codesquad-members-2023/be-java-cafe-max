package kr.codesqaud.cafe.dto;

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
