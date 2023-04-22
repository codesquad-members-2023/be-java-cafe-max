package kr.codesqaud.cafe.user.controller.request;

public class SignInRequestDTO {
	private final String userId;
	private final String password;
	private final boolean remember;

	public SignInRequestDTO(String userId, String password, String remember) {
		this.userId = userId;
		this.password = password;
		this.remember = remember != null;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public boolean isRemember() {
		return remember;
	}
}
