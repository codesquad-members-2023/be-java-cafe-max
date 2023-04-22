package kr.codesqaud.cafe.domain.auth.dto.request;

public class AuthLoginRequestDto {

	private String username;
	private String password;

	public AuthLoginRequestDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
