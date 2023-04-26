package kr.codesqaud.cafe.dto;

import java.time.LocalDate;

public class UserResponse {
	private Long userIndex;
	private String userID;
	private String email;
	private String nickname;
	private String password;
	private LocalDate signUpDate;

	public UserResponse(Long userIndex, String userID, String email, String nickname, String password,
		LocalDate signUpDate) {
		this.userIndex = userIndex;
		this.userID = userID;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.signUpDate = signUpDate;
	}

	public String getUserID() {
		return userID;
	}

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public Long getUserIndex() {
		return userIndex;
	}

	public LocalDate getSignUpDate() {
		return signUpDate;
	}
}
