package kr.codesqaud.cafe.exception;

public class ArticleNotFoundException extends RuntimeException {
	public ArticleNotFoundException() {
		super("글 정보를 찾을 수 없습니다.");
	}
}
