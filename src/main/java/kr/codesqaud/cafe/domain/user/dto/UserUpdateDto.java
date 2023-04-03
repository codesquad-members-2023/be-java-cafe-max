package kr.codesqaud.cafe.domain.user.dto;

import kr.codesqaud.cafe.domain.user.entity.User;

public class UserUpdateDto {

	private String name;
	private String email;
	private String password;

	public UserUpdateDto(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public User toEntity(Long id) {
		User convertedUser = new User(name, password, email);
		convertedUser.setId(id);
		return convertedUser;
	}

}
