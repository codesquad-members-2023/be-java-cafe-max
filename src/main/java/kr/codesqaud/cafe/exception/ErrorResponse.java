package kr.codesqaud.cafe.exception;

public class ErrorResponse {
	private String detail;

	public ErrorResponse(String detail) {
		this.detail = detail;
	}

	public String getDetail() {
		return detail;
	}
}
