package kr.codesqaud.cafe.account.dto;

public class UserListResponse {
	private final String nickName;
	private final String email;
	private final String id;
	private final String date;

	public UserListResponse(String nickName, String email, String id, String date) {
		this.nickName = nickName;
		this.email = email;
		this.id = id;
		this.date = date;
	}
}
