package kr.codesqaud.cafe.account.dto;

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
}
