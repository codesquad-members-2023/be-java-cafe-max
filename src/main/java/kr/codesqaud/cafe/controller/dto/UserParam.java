package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.user.User;

public class UserParam {

	private final String userId;
	private final String name;
	private final String email;

	private UserParam(String userId, String name, String email) {
		this.userId = userId;
		this.name = name;
		this.email = email;
	}

	public static UserParam from(final User user) {
		return new UserParam(user.getUserId(), user.getName(), user.getEmail());
	}
}
