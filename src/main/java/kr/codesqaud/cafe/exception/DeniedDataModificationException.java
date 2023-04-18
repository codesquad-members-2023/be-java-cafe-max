package kr.codesqaud.cafe.exception;

public class DeniedDataModificationException extends RuntimeException {
	public DeniedDataModificationException(String message) {
		super(message);
	}
}
