package kr.codesqaud.cafe.domain.user.dto.request;

import kr.codesqaud.cafe.domain.user.entity.User;

public class UserUpdateRequestDto {

	private String id;
	private String name;
	private String email;
	private String password;

	public UserUpdateRequestDto(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public User toEntity(String id) {
		User convertedUser = new User(id, name, password, email);
		convertedUser.setId(id);
		return convertedUser;
	}

}
