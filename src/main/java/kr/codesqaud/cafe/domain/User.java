package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.dto.UserDTO;

public class User {
	private int idx;
	private String userId;
	private String password;
	private String name;
	private String email;

	public User(int idx, String userId, String password, String name, String email) {
		this.idx = idx;
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public int getIdx() {
		return idx;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public UserDTO toDto() {
		return new UserDTO(idx, userId, name, email);
	}

	public void updateFrom(SignUpDTO dto) {
		this.password = dto.getPassword();
		this.name = dto.getName();
		this.email = dto.getEmail();
	}

	@Override
	public String toString() {
		return "User{" +
			"userId='" + userId + '\'' +
			", password='" + password + '\'' +
			", name='" + name + '\'' +
			", email='" + email + '\'' +
			'}';
	}
}
