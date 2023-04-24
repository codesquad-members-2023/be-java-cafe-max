package kr.codesqaud.cafe.account.exception;

public class IdDuplicatedException extends RuntimeException {

	private static final String ID_DUPLICATED_EXCEPTION = "중복된 아이디가 존재합니다.";

	public IdDuplicatedException() {
		super(ID_DUPLICATED_EXCEPTION);
	}
}

