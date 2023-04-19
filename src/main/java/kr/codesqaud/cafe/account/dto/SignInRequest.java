package kr.codesqaud.cafe.account.dto;

import java.util.Objects;

public class SignInRequest {

	private final String id;

	private final String password;

	public SignInRequest(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public boolean isMatchWithResponsePassword(String responsePassword) {
		return Objects.equals(password, responsePassword);
	}
}
