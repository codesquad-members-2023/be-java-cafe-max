package kr.codesqaud.cafe.common.auth.exception;

public class NoAuthSessionException extends Exception {
	public NoAuthSessionException() {
		super("로그인이 필요한 페이지 입니다.");
	}
}
