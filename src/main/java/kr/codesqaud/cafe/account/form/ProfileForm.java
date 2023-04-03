package kr.codesqaud.cafe.account.form;

public class ProfileForm {
	private String nickname;
	private String email;

	private ProfileForm(Builder builder) {
		this.nickname = builder.nickname;
		this.email = builder.email;
	}

	public String getNickname() {
		return nickname;
	}

	public String getEmail() {
		return email;
	}

	public static class Builder {
		private String nickname;
		private String email;

		public Builder nickname(String nickname) {
			this.nickname = nickname;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public ProfileForm build() {
			return new ProfileForm(this);
		}
	}
}
