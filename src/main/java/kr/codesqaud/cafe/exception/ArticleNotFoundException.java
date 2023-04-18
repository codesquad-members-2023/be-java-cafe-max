package kr.codesqaud.cafe.exception;

public class ArticleNotFoundException extends RuntimeException {
	public ArticleNotFoundException(String message) {
		super(message);
	}
}
