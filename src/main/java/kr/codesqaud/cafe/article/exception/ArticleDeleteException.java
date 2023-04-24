package kr.codesqaud.cafe.article.exception;

public class ArticleDeleteException extends RuntimeException {
	private static final String ARTICLE_DELETE_EXCEPTION = "해당 게시글을 삭제할수 없습니다.";

	public ArticleDeleteException() {
		super(ARTICLE_DELETE_EXCEPTION);
	}
}
