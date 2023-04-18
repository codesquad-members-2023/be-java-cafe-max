package kr.codesqaud.cafe.user.controller.response;

import kr.codesqaud.cafe.user.domain.User;

public class UserResponseDTO {
	private final long id;
	private final String userId;
	private final String name;
	private final String email;

	public UserResponseDTO(long id, String userId, String name, String email) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public static UserResponseDTO from(User user) {
		return new UserResponseDTO(user.getId(), user.getUserId(), user.getName(), user.getEmail());
	}

}
