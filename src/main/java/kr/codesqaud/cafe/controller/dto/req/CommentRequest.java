package kr.codesqaud.cafe.controller.dto.req;

import kr.codesqaud.cafe.domain.comment.Comment;

public class CommentRequest {

	private Long articleId;
	private String content;

	public CommentRequest() {
	}

	public CommentRequest(Long articleId, String content) {
		this.articleId = articleId;
		this.content = content;
	}

	public Long getArticleId() {
		return articleId;
	}

	public String getContent() {
		return content;
	}

	public Comment toEntity(final String userId) {
		return Comment.of(content, userId, articleId);
	}
}
