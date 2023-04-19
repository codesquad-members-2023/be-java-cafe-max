package kr.codesqaud.cafe.domain.auth.dto.request;

public class AuthLoginRequestDto {

	private String loginId;
	private String password;

	public AuthLoginRequestDto(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getPassword() {
		return password;
	}
}
