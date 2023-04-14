package kr.codesqaud.cafe.controller.dto;

public class ModifyRequest {
	private final String userId;
	private final String oriPassword;
	private final String newPassword;
	private final String name;
	private final String email;

	public ModifyRequest(String userId, String oriPassword, String newPassword, String name, String email) {
		this.userId = userId;
		this.oriPassword = oriPassword;
		this.newPassword = newPassword;
		this.name = name;
		this.email = email;
	}

	public String getUserId() {
		return userId;
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
