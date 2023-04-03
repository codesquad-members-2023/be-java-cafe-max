package kr.codesqaud.cafe.domain.user.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {

	private String password;
	private String name;
	private String email;
	private Long id;
	String dateTime;

	public User(String name, String password, String email) {
		this.password = password;
		this.name = name;
		this.email = email;
		this.dateTime = setDateTime();
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

	public String setDateTime() {
		LocalDateTime now = LocalDateTime.now();
		return now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E) HH:mm:ss"));
	}
}
