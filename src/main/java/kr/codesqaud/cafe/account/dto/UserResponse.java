package kr.codesqaud.cafe.account.dto;

public class UserResponse {

	private final String nickName;

	private final String email;

	private final String password;

	private final String userId;

	public UserResponse(String nickName, String email, String password, String userId) {
		this.nickName = nickName;
		this.email = email;
		this.password = password;
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public String getNickName() {
		return nickName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
