package kr.codesqaud.cafe.exception.article;

public class ArticleIdAndSessionIdMismatchException extends RuntimeException {
	private static final String ARTICLE_ID_AND_SESSION_ID_MISMATCH_EXCEPTION = "수정또는 삭제 할 수 있는 권한이 없습니다.";

	public ArticleIdAndSessionIdMismatchException() {
		super(ARTICLE_ID_AND_SESSION_ID_MISMATCH_EXCEPTION);
	}
}
