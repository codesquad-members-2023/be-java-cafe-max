package kr.codesqaud.cafe.global.common.constant;

public enum SessionAttributeNames {

	LOGIN_USER_ID("loginUserId"),
	LOGIN_USER_NAME("loginUserName");

	private final String type;

	SessionAttributeNames(String type) {
		this.type = type;
	}

	public String type() {
		return type;
	}
}
