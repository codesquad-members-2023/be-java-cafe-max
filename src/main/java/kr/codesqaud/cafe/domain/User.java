package kr.codesqaud.cafe.domain;

public class User {
	private String userId;
	private String password;
	private String name;
	private String email;

	public User(String userId, String password, String name, String email) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public User(String userId, String name, String email) {
		this(userId, null, name, email);
	}

	public User(String userId, String password) {
		this(userId, password, null, null);
	}

	public boolean isSamePassword(String password) {
		return this.password.equals(password);
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
}
