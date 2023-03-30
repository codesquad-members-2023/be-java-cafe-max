package kr.codesqaud.cafe.account.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ProfileSettingForm {
	@NotNull
	@Size(max = 64,min = 2)
	private String nickname;
	@NotNull
	@Email
	private String email;
	@NotNull
	@Size(max = 32, min = 8)
	@Pattern(regexp = "^(.*[a-z]+.*[1-9]+.*)|(.*[1-9]+.*[a-z]+.*)$")
	private String password;

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
