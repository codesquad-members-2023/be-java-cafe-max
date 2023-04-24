package kr.codesqaud.cafe.common.auth.exception;

public class NoAccessPermissionException extends Exception {
	public NoAccessPermissionException() {
		super("접근 권한이 없습니다.");
	}
}
