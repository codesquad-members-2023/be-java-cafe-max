package kr.codesqaud.cafe.account.dto;

public class UserListResponse {
	private final String nickName;
	private final String email;
	private final String userId;
	private final String date;

	public UserListResponse(String nickName, String email, String userId, String date) {
		this.nickName = nickName;
		this.email = email;
		this.userId = userId;
		this.date = date;
	}
}
