package kr.codesqaud.cafe.domain.user.dto.request;

import kr.codesqaud.cafe.domain.user.entity.User;

public class UserSaveRequestDto {

	private String loginId;
	private String name;
	private String password;
	private String email;

	public UserSaveRequestDto(String loginId, String name, String password, String email) {
		this.loginId = loginId;
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public User toEntity() {
		return new User(loginId, name, password, email);
	}

	public String getLoginId() {
		return loginId;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
}
