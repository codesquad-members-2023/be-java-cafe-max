package kr.codesqaud.cafe.domain.user;

import kr.codesqaud.cafe.controller.dto.req.JoinRequest;

public class User {

	private String userId;
	private String password;
	private String name;
	private String email;

	public User(String userId, String password, String name, String email) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public static User from(final JoinRequest joinRequest) {
		return new User(joinRequest.getUserId(),
			joinRequest.getPassword(),
			joinRequest.getName(),
			joinRequest.getEmail());
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public boolean isSamePassword(final String password) {
		return this.password.equals(password);
	}

	public void editProfile(final String password, final String name, final String email) {
		this.password = password;
		this.name = name;
		this.email = email;
	}
}
