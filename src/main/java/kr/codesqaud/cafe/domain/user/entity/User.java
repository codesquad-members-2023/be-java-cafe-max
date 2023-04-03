package kr.codesqaud.cafe.domain.user.entity;

import java.time.LocalDateTime;

public class User {

	private String password;
	private String name;
	private String email;
	private Long id;
	LocalDateTime dateTime;

	public User(String name, String password, String email) {
		this.password = password;
		this.name = name;
		this.email = email;
		this.dateTime = LocalDateTime.now();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

}
