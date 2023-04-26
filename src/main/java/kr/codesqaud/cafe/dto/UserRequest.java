package kr.codesqaud.cafe.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {
	@Size(min = 2, max = 64, message = "아이디는 2글자에서 64글자 사이로 적어주세요.", groups = ValidationGroups.userCreateValidationGroup.class)
	private String userID;
	@Email(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "이메일 규격에 맞게 작성해주세요.", groups = {
		ValidationGroups.userCreateValidationGroup.class, ValidationGroups.userUpdateValidationGroup.class})
	private String email;
	@Size(min = 2, max = 64, message = "닉네임은 2글자에서 64글자 사이로 적어주세요.", groups = {
		ValidationGroups.userCreateValidationGroup.class, ValidationGroups.userUpdateValidationGroup.class})
	private String nickname;
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z]).{8,32}", message = "비밀번호는 8글자에서 32글자 사이로 영어 소문자 및 숫자 반드시 포함해야 합니다.", groups = {
		ValidationGroups.userCreateValidationGroup.class, ValidationGroups.userUpdateValidationGroup.class})
	private String password;

	public UserRequest(String userID, String email, String nickname, String password) {
		this.userID = userID;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}

	public String getUserID() {
		return userID;
	}

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}
}
