package kr.codesqaud.cafe.exception;

public class DuplicatedUserIdException extends RuntimeException {

	public DuplicatedUserIdException() {
		super("해당 아이디는 이미 존재합니다.");
	}
}
