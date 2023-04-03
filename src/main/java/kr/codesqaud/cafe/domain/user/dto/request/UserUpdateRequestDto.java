package kr.codesqaud.cafe.domain.user.dto.request;

import kr.codesqaud.cafe.domain.user.entity.User;

public class UserUpdateRequestDto {

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

	public User toEntity(Long id) {
		User convertedUser = new User(name, password, email);
		convertedUser.setId(id);
		return convertedUser;
	}

}
