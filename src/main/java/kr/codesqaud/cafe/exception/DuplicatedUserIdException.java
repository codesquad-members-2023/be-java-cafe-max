package kr.codesqaud.cafe.exception;

public class DuplicatedUserIdException extends RuntimeException {
	public DuplicatedUserIdException() {
		super("중복된 ID 입니다.");
	}
}
