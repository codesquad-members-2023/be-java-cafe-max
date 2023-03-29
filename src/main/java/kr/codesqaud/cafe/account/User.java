package kr.codesqaud.cafe.account;

import java.util.concurrent.atomic.AtomicLong;

import kr.codesqaud.cafe.account.form.ProfileForm;
import kr.codesqaud.cafe.account.form.UsersForm;

public class User {

	private static final AtomicLong number = new AtomicLong();
	private final Long id;
	private String nickname;
	private String email;
	private String password;

	public User(long id) {
		this.id = id;
	}

	public static long createNewId() {
		return number.getAndIncrement();
	}

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

	public UserForm mappingUserForm() {
		UserForm userForm = new UserForm();
		userForm.setNickname(getNickname());
		userForm.setEmail(getEmail());
		return userForm;
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
}
