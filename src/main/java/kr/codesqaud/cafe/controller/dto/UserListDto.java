package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.user.User;

public class UserListDto {

	private final Long rowIndex;
	private final String userId;
	private final String name;
	private final String email;

	private UserListDto(Long rowIndex, String userId, String name, String email) {
		this.rowIndex = rowIndex;
		this.userId = userId;
		this.name = name;
		this.email = email;
	}

	public static UserListDto from(User user, final long rowIndex) {
		return new UserListDto(rowIndex, user.getUserId(), user.getName(), user.getEmail());
	}
}
