package kr.codesqaud.cafe.domain.user.dto.request;

import kr.codesqaud.cafe.domain.user.entity.User;

public class UserSaveRequestDto {

	private String username;
	private String nickName;
	private String password;
	private String email;

	public UserSaveRequestDto(String username, String nickName, String password, String email) {
		this.username = username;
		this.nickName = nickName;
		this.password = password;
		this.email = email;
	}

	public User toEntity() {
		return new User(username, nickName, password, email);
	}

	public String getUsername() {
		return username;
	}

	public String getNickName() {
		return nickName;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
}
