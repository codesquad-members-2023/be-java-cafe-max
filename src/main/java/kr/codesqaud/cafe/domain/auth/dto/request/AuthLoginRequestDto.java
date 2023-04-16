package kr.codesqaud.cafe.domain.auth.dto.request;

public class AuthLoginRequestDto {

	private String id;
	private String password;

	public AuthLoginRequestDto(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
}
