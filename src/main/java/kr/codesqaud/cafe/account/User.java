package kr.codesqaud.cafe.account;

import java.util.concurrent.atomic.AtomicLong;

public class User {

	private static AtomicLong number;
	private final Long id;
	private String nickname;
	private String email;
	private String password;

	public User(long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public static long createNewId() {
		return number.getAndIncrement();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
