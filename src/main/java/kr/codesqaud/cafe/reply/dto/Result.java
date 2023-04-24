package kr.codesqaud.cafe.reply.dto;

public class Result {
	private boolean ok;
	private String errorMessage;

	public Result(boolean ok, String errorMessage) {
		this.ok = ok;
		this.errorMessage = errorMessage;
	}

	public boolean isOk() {
		return ok;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public static Result ok() {
		return new Result(true, null);
	}

	public static Result fail(String errorMessage) {
		return new Result(false, errorMessage);
	}
}