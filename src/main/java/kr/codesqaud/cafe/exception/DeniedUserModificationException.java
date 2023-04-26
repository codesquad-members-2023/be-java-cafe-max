package kr.codesqaud.cafe.exception;

public class DeniedUserModificationException extends RuntimeException {
	public DeniedUserModificationException() {
		super("다른 사람의 정보는 수정할 수 없습니다.");
	}
}
