package kr.codesqaud.cafe.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	INVALID_USER_ID_CODE(HttpStatus.BAD_REQUEST, "US001", "접근 불가능한 User 아이디입니다.", "error/custom"),
	NO_SUCH_USER_ID_CODE(HttpStatus.NOT_FOUND, "UD001", "해당 User 아이디가 존재하지 않습니다.", "error/custom"),
	NO_SUCH_EMAIL_CODE(HttpStatus.NOT_FOUND, "UD002", "해당 User 이메일이 존재하지 않습니다.", "error/custom"),
	SAVE_USER_FAILED_CODE(HttpStatus.BAD_REQUEST, "UD003", "해당 User 정보를 저장하는데 실패 했습니다.", "error/custom"),
	GET_ALL_USERS_FAILED_CODE(HttpStatus.BAD_REQUEST, "UD004", "User 전체 조회 실패 했습니다.", "error/custom"),
	UPDATE_USER_FAILED_CODE(HttpStatus.BAD_REQUEST, "UD005", "User 업데이트 실패 했습니다.", "error/custom"),
	INVALID_POST_ID_CODE(HttpStatus.BAD_REQUEST, "PS001", "접근 불가능한 Post 아이디입니다.", "error/custom"),
	SAVE_POST_FAILED_CODE(HttpStatus.BAD_REQUEST, "PD001", "해당 Post 정보를 저장하는데 실패 했습니다.", "error/custom"),
	NO_SUCH_POST_ID_CODE(HttpStatus.NOT_FOUND, "PD002", "해당 Post 아이디가 존재하지 않습니다.", "error/custom"),
	GET_ALL_POSTS_FAILED_CODE(HttpStatus.NOT_FOUND, "PD003", "Post 전체 조회 실패 했습니다.", "error/custom"),
	NO_SUCH_POST_TITLE_CODE(HttpStatus.NOT_FOUND, "PD004", "해당 Post 타이틀이 존재하지 않습니다.", "error/custom"),
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
