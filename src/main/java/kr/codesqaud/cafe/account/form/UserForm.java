package kr.codesqaud.cafe.account.form;

public class UserForm {
	private Long id;
	private String nickname;
	private String email;

	public UserForm() {
	}

	private UserForm(Builder builder) {
		this.id = builder.id;
		this.nickname = builder.nickname;
		this.email = builder.email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public static class Builder {
		private String nickname;
		private String email;
		private Long id;

		public Builder nickname(String nickname) {
			this.nickname = nickname;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public UserForm build() {
			return new UserForm(this);
		}
	}
}
