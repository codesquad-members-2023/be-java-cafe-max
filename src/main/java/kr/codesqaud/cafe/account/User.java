package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.form.ProfileForm;
import kr.codesqaud.cafe.account.form.ProfileSettingForm;
import kr.codesqaud.cafe.account.form.UsersForm;

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

	public UsersForm mappingUsersForm() {
		UsersForm usersForm = new UsersForm();
		usersForm.setId(getId());
		usersForm.setEmail(getEmail());
		usersForm.setNickname(getNickname());
		return usersForm;
	}

	public ProfileForm mappingProfileForm() {
		ProfileForm profileForm = new ProfileForm();
		profileForm.setEmail(getEmail());
		profileForm.setNickname(getNickname());
		return profileForm;
	}

	public ProfileSettingForm mappingProfileSettingFormWithPassword() {
		ProfileSettingForm profileSettingForm = new ProfileSettingForm();
		profileSettingForm.setEmail(getEmail());
		profileSettingForm.setNickname(getNickname());
		return profileSettingForm;
	}
}
