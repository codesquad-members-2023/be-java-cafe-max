package kr.codesqaud.cafe.controller.dto.req;

public class ProfileEditRequest {

	private final String oriPassword;
	private final String newPassword;
	private final String name;
	private final String email;

	public ProfileEditRequest(String oriPassword, String newPassword, String name, String email) {
		this.oriPassword = oriPassword;
		this.newPassword = newPassword;
		this.name = name;
		this.email = email;
	}

	public String getOriPassword() {
		return oriPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
}
