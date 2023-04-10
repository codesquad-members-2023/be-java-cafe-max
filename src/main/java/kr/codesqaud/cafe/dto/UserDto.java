package kr.codesqaud.cafe.dto;

public class UserDto {
	private String userID;
	private String email;
	private String nickname;
	private String password;

	public UserDto(String userID, String email, String nickname, String password) {
		this.userID = userID;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}

	public String getUserID() {
		return userID;
	}

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}
}
