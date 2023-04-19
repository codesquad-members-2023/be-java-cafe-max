package kr.codesqaud.cafe.domain.user.entity;

import java.time.LocalDateTime;

public class User {
	private String id;
	private String password;
	private String name;
	private String email;
	private String loginId;
	LocalDateTime dateTime;

	public User(String loginId, String name, String password, String email) {
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.dateTime = LocalDateTime.now();
	}

	public User() {
	}

	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}

	public String getName() {
		return name;
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

	public void setName(String name) {
		this.name = name;
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

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
