package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.user.User;

public class UserDto {

	private final Long rowIndex;
	private final String userId;
	private final String name;
	private final String email;

	private UserDto(Long rowIndex, String userId, String name, String email) {
		this.rowIndex = rowIndex;
		this.userId = userId;
		this.name = name;
		this.email = email;
	}

	public static UserDto from(final User user) {
		return from(user, 0);
	}

	public static UserDto from(final User user, final long rowIndex) {
		return new UserDto(rowIndex, user.getUserId(), user.getName(), user.getEmail());
	}
}
