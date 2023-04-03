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

	public void setId(Long id) {
		this.id = id;
	}

	public UserForm mappingUsersForm() {
		UserForm userForm = new UserForm();
		userForm.setId(getId());
		userForm.setEmail(getEmail());
		userForm.setNickname(getNickname());
		return userForm;
	}

	public ProfileForm mappingProfileForm() {
		return new ProfileForm.Builder()
			.email(email)
			.nickname(nickname)
			.build();
	}

	public ProfileSettingForm mappingProfileSettingFormWithPassword() {
		ProfileSettingForm profileSettingForm = new ProfileSettingForm();
		profileSettingForm.setEmail(getEmail());
		profileSettingForm.setNickname(getNickname());
		return profileSettingForm;
	}

	private User(Builder builder) {
		this.id = builder.id;
		this.email = builder.email;
		this.password = builder.password;
		this.nickname = builder.nickname;
	}

	public static class Builder {
		private Long id;
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
