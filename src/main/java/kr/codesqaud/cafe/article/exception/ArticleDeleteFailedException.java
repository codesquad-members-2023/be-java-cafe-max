package kr.codesqaud.cafe.article.exception;

public class ArticleDeleteFailedException extends RuntimeException {
	private static final String ARTICLE_DELETE_FAILED_EXCEPTION = "해당 게시글을 삭제할수 없습니다.";

	public ArticleDeleteFailedException() {
		super(ARTICLE_DELETE_FAILED_EXCEPTION);
	}
}
