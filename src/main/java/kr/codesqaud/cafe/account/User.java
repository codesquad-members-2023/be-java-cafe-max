package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.form.ProfileForm;
import kr.codesqaud.cafe.account.form.ProfileSettingForm;
import kr.codesqaud.cafe.account.form.UserForm;

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

	public UserForm mappingUsersForm() {
		return new UserForm.Builder()
			.id(id)
			.email(email)
			.nickname(nickname)
			.build();
	}

	public ProfileForm mappingProfileForm() {
		return new ProfileForm.Builder()
			.email(email)
			.nickname(nickname)
			.build();
	}

	public ProfileSettingForm mappingProfileSettingFormWithPassword() {
		return new ProfileSettingForm.Builder()
			.email(email)
			.nickname(nickname)
			.password(password)
			.build();
	}

	private User(Builder builder) {
		this.id = builder.id;
		this.email = builder.email;
		this.password = builder.password;
		this.nickname = builder.nickname;
	}

	public void setting(ProfileSettingForm profileSettingForm) {
		this.nickname = profileSettingForm.getNickname();
		this.email = profileSettingForm.getEmail();
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
