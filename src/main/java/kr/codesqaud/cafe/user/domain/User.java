package kr.codesqaud.cafe.user.domain;

import kr.codesqaud.cafe.user.dto.request.SignUpRequestDTO;
import kr.codesqaud.cafe.user.dto.response.UserResponseDTO;

public class User {
	private final int idx;
	private final String userId;
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

	public UserResponseDTO toDto() {
		return new UserResponseDTO(idx, userId, name, email);
	}

	public void updateFrom(SignUpRequestDTO dto) {
		this.password = dto.getPassword();
		this.name = dto.getName();
		this.email = dto.getEmail();
	}

}
