package kr.codesqaud.cafe.account.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	INVALID_ID_CODE(HttpStatus.BAD_REQUEST, "U001", "해당 아이디가 없습니다.", "error/custom"),
	NO_EXIST_ID_CODE(HttpStatus.BAD_REQUEST, "U002", "해당 아이디가 존재하지 않습니다.", "error/custom");

	private final HttpStatus status;
	private final String code;
	private final String message;
	private final String viewName;

	ErrorCode(HttpStatus status, String code, String message, String viewName) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.viewName = viewName;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getViewName() {
		return viewName;
	}
}
