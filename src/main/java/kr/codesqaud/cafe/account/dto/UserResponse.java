package kr.codesqaud.cafe.account.dto;

public class UserResponse {

	private final String nickName;

	private final String email;

	private final String password;

	private final String id;

	public UserResponse(String nickName, String email, String password, String id) {
		this.nickName = nickName;
		this.email = email;
		this.password = password;
		this.id = id;
	}

	public String getId() {
		return id;
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
