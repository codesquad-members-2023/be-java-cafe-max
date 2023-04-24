package kr.codesqaud.cafe.global.config;

import kr.codesqaud.cafe.account.dto.UserResponse;

public class Session {
	public static final String LOGIN_USER = "loginUser";

	private final String id;
	private final String nickName;

	public Session(String id, String nickName) {
		this.id = id;
		this.nickName = nickName;
	}

	public Session(UserResponse userResponse) {
		this.id = userResponse.getUserId();
		this.nickName = userResponse.getNickName();
	}

	public String getId() {
		return id;
	}

	public String getNickName() {
		return nickName;
	}
}
