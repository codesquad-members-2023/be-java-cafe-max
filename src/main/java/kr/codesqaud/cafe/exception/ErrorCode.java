package kr.codesqaud.cafe.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	INVALID_ID_CODE(HttpStatus.BAD_REQUEST, "S001", "해당 아이디가 없습니다.", "error/custom"),
	NO_SUCH_ID_CODE(HttpStatus.BAD_REQUEST, "D001", "해당 아이디가 존재하지 않습니다.", "error/custom"),
	NO_SUCH_EMAIL_CODE(HttpStatus.BAD_REQUEST, "D002", "해당 이메일이 존재하지 않습니다.", "error/custom"),
	SAVE_USER_FAILED_CODE(HttpStatus.BAD_REQUEST, "D003", "해당 정보를 저장에 실패 했습니다.", "error/custom"),
	GET_ALL_USERS_FAILED_CODE(HttpStatus.BAD_REQUEST, "D004", "유저 전체 조회 실패 했습니다.", "error/custom"),
	UPDATE_USER_FAILED_CODE(HttpStatus.BAD_REQUEST, "D005", "유저 업데이트 실패 했습니다.", "error/custom"),
	SAVE_POST_FAILED_CODE(HttpStatus.BAD_REQUEST, "D006", "Post 정보를 저장에 실패 했습니다.", "error/custom"),
	INVALID_POST_ID_CODE(HttpStatus.NOT_FOUND, "D007", "존재하지 않는 Post입니다.", "error/custom"),
	;

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
