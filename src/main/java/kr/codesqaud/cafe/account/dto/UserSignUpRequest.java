package kr.codesqaud.cafe.account.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class UserSignUpRequest {

	@Length(min = 2, max = 12)
	private final String nickName;

	@NotBlank
	@Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
	private final String email;

	@NotBlank
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
	private final String password;

	@NotBlank
	@Length(min = 2, max = 12)
	private final String userId;

	public UserSignUpRequest(String nickName, String email, String password, String userId) {
		this.nickName = nickName;
		this.email = email;
		this.password = password;
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public String getNickName() {
		return nickName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}
