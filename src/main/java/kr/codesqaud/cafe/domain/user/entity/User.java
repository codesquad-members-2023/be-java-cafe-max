package kr.codesqaud.cafe.domain.user.entity;

import java.time.LocalDateTime;

public class User {
	private String id;
	private String password;
	private String nickName;
	private String email;
	private String username;
	LocalDateTime dateTime;

	public User(String username, String nickName, String password, String email) {
		this.username = username;
		this.password = password;
		this.nickName = nickName;
		this.email = email;
		this.dateTime = LocalDateTime.now();
	}

	public User() {
	}

	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}

	public String getNickName() {
		return nickName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return this.email;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
