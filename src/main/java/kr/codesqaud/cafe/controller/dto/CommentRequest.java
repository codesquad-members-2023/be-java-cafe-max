package kr.codesqaud.cafe.controller.dto;

public class CommentRequest {
	private final Long articleId;
	private final String contents;

	public CommentRequest(Long articleId, String contents) {
		this.articleId = articleId;
		this.contents = contents;
	}

	public Long getArticleId() {
		return articleId;
	}

	public String getContents() {
		return contents;
	}
}
