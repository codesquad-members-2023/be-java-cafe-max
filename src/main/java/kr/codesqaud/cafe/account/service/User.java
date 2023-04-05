package kr.codesqaud.cafe.account.service;

public class User {

	private final Long id;
	private final String nickname;
	private final String email;
	private final String password;

	private User(Builder builder) {
		this.id = builder.id;
		this.email = builder.email;
		this.password = builder.password;
		this.nickname = builder.nickname;
	}

	public Long getId() {
		return id;
	}

	public String getNickname() {
		return nickname;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public static class Builder {
		private Long id = 0L;
		private String nickname = "";
		private String email = "";
		private String password = "";

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder nickname(String nickname) {
			this.nickname = nickname;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}

}
