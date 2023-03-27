package kr.codesqaud.cafe.account;

public class User {
	private String nickname;
	private String email;
	private String password;

	public User(String nickname, String email, String password) {
		this.nickname = nickname;
		this.email = email;
		this.password = password;
	}

	public User() {
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
