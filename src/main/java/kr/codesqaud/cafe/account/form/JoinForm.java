package kr.codesqaud.cafe.account.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import kr.codesqaud.cafe.account.User;
import kr.codesqaud.cafe.account.UserRepository;

public class JoinForm {
	@NotEmpty
	@Size(max = 64, min = 2, message = "{error.nickname.size}")
	private String nickname;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	@Size(max = 32, min = 8, message = "{error.password.size}")
	@Pattern(regexp = "^(.*[a-z]+.*[1-9]+.*)|(.*[1-9]+.*[a-z]+.*)$", message = "{error.password.pattern}")
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

	public User toUser() {
		return new User.Builder(UserRepository.atomicKey.incrementAndGet())
			.nickname(nickname)
			.email(email)
			.password(password)
			.build();
	}
}
