package kr.codesqaud.cafe.account.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ProfileSettingForm {
	@NotEmpty
	@Size(max = 64, min = 2, message = "닉네임을 정확히 입력해주세요(글자수 2~64)")
	private String nickname;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	@Size(max = 32, min = 8, message = "비밀번호를 정확히 입력해주세요(8글자 이상 32글자 이하)")
	@Pattern(regexp = "^(.*[a-z]+.*[1-9]+가.*)|(.*[1-9]+.*[a-z]+.*)$", message = "비밀번호를 정확히 입력해주세요(영어 소문자 및 숫자 반드시 포함)")
	private String password;

	public ProfileSettingForm() {
	}

	private ProfileSettingForm(Builder builder) {
		this.email = builder.email;
		this.nickname = builder.nickname;
		this.password = builder.password;
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

	public static class Builder {
		private String nickname;
		private String email;
		private String password;

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

		public ProfileSettingForm build() {
			return new ProfileSettingForm(this);
		}
	}
}
