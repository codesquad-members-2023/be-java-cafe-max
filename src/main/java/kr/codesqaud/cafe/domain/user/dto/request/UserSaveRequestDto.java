package kr.codesqaud.cafe.domain.user.dto.request;

import kr.codesqaud.cafe.domain.user.entity.User;

public class UserSaveRequestDto {

	private String id;
	private String name;
	private String password;
	private String email;

	public UserSaveRequestDto(String id, String name, String password, String email) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public User toEntity() {
		return new User(id, name, password, email);
	}

}
