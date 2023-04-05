package kr.codesqaud.cafe.account.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginForm {
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	@Size(max = 32, min = 8, message = "{error.password.size}")
	@Pattern(regexp = "^(.*[a-z]+.*[1-9]+가.*)|(.*[1-9]+.*[a-z]+.*)$", message = "{error.password.pattern}")
	private String password;

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
