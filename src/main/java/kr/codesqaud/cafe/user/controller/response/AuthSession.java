package kr.codesqaud.cafe.user.controller.response;

import kr.codesqaud.cafe.user.domain.UserEntity;

public class AuthSession {
	private final long id;
	private final String userId;

	public AuthSession(long id, String userId) {
		this.id = id;
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public static AuthSession from(UserEntity user) {
		return new AuthSession(user.getId(), user.getUserId());
	}
}
