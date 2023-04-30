package kr.codesqaud.cafe.user.dto;

import kr.codesqaud.cafe.user.User;

public class ProfileResponseDto {

    private final String name;
    private final String email;

    public ProfileResponseDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static ProfileResponseDto from(User user) {
        String name = user.getName();
        String email = user.getEmail();
        return new ProfileResponseDto(name, email);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
