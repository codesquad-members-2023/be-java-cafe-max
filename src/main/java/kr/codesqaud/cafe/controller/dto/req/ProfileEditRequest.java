package kr.codesqaud.cafe.controller.dto.req;

public class ProfileEditRequest {

	private final String originalPassword;
	private final String newPassword;
	private final String name;
	private final String email;

	public ProfileEditRequest(String originalPassword, String newPassword, String name, String email) {
		this.originalPassword = originalPassword;
		this.newPassword = newPassword;
		this.name = name;
		this.email = email;
	}

	public String getOriginalPassword() {
		return originalPassword;
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
