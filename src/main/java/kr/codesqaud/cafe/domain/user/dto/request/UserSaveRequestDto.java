package kr.codesqaud.cafe.domain.user.dto.request;

import kr.codesqaud.cafe.domain.user.entity.User;

public class UserSaveRequestDto {

	private String name;
	private String password;
	private String email;

	public UserSaveRequestDto(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public User toEntity() {
		return new User(name, password, email);
	}

}
