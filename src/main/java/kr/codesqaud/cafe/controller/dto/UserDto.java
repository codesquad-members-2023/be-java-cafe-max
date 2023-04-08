package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.User;

public class UserDto {
    private final String userId;
    private final String userName;
    private final String userEmail;

    private UserDto(String userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public static UserDto from(final User user) {
        return new UserDto(user.getUserId(), user.getUserName(), user.getUserEmail());
    }
}
