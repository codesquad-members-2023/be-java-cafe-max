package kr.codesqaud.cafe.domain.user.dto.request;

import kr.codesqaud.cafe.domain.user.entity.User;

public class UserUpdateRequestDto {

	private String id;
	private String password;
	private String changedName;
	private String changedEmail;

	public UserUpdateRequestDto(String password, String changedName, String changedEmail) {
		this.password = password;
		this.changedName = changedName;
		this.changedEmail = changedEmail;
	}

	public User toEntity(String loginId) {
		return new User(loginId, this.changedName, this.password, this.changedEmail);
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getChangedName() {
		return changedName;
	}

	public String getChangedEmail() {
		return changedEmail;
	}
}
