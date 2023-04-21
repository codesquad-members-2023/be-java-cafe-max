package kr.codesqaud.cafe.user.controller.request;

public class SignInRequestDTO {
	private final String userId;
	private final String password;

	public SignInRequestDTO(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}
}
