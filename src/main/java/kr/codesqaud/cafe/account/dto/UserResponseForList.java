package kr.codesqaud.cafe.account.dto;

public class UserResponseForList {
	private final String nickName;
	private final String email;
	private final String userId;
	private final String date;

	public UserResponseForList(String nickName, String email, String userId, String date) {
		this.nickName = nickName;
		this.email = email;
		this.userId = userId;
		this.date = date;
	}
}
