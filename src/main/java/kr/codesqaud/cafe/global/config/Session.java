package kr.codesqaud.cafe.global.config;

import javax.servlet.http.HttpSession;

public class Session {
	public static final String LOGIN_USER = "loginUser";

	private final String id;
	private final String nickName;

	public Session(String id, String nickName) {
		this.id = id;
		this.nickName = nickName;
	}

	public String getId() {
		return id;
	}

	public String getNickName() {
		return nickName;
	}

	public static Session getLoginUser(HttpSession httpSession) {
		Object sessionObject = httpSession.getAttribute(Session.LOGIN_USER);
		if (sessionObject instanceof Session) {
			return (Session)sessionObject;
		}
		return null;
	}
}
