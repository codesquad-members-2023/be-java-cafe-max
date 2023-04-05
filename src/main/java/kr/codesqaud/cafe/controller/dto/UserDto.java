package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.user.User;

public class UserDto {

	private final String userId;
	private final String name;
	private final String email;

	private UserDto(String userId, String name, String email) {
		this.userId = userId;
		this.name = name;
		this.email = email;
	}

	public static UserDto from(final User user) {
		return new UserDto(user.getUserId(), user.getName(), user.getEmail());
	}
}
