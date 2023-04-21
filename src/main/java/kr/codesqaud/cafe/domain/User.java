package kr.codesqaud.cafe.domain;

import java.time.LocalDate;

import kr.codesqaud.cafe.exception.DeniedDataModificationException;
import kr.codesqaud.cafe.exception.InvalidPasswordException;

public class User {
	private Long userIndex;
	private String userID;
	private String email;
	private String nickname;
	private String password;
	private LocalDate signUpDate;

	public User(Long userIndex, String userID, String email, String nickname, String password, LocalDate signUpDate) {
		this.userIndex = userIndex;
		this.userID = userID;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.signUpDate = signUpDate;
	}

	public User(String userID, String email, String nickname, String password, LocalDate signUpDate) {
		this.userID = userID;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.signUpDate = signUpDate;
	}

	public Long getUserIndex() {
		return userIndex;
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

	public LocalDate getSignUpDate() {
		return signUpDate;
	}

	public void validateUserId(String userID) {
		if (!this.userID.equals(userID)) {
			throw new DeniedDataModificationException("다른 사람의 정보는 수정할 수 없습니다.");
		}
	}

	public void validatePassword(String password) {
		if (!this.password.equals(password)) {
			throw new InvalidPasswordException();
		}
	}
}
