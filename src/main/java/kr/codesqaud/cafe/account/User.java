package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.form.ProfileSettingForm;

public class User {

	private Long id;
	private String nickname;
	private String email;
	private String password;

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

	private User(Builder builder) {
		this.id = builder.id;
		this.email = builder.email;
		this.password = builder.password;
		this.nickname = builder.nickname;
	}

	public static class Builder {
		private final Long id;
		private String nickname;
		private String email;
		private String password;

		public Builder(Long id) {
			this.id = id;
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
