package kr.codesqaud.cafe.controller.dto.req;

import kr.codesqaud.cafe.domain.articlecomment.Comment;

public class ReplyRequest {

	private Long articleId;
	private String content;

	public ReplyRequest() {
	}

	public ReplyRequest(Long articleId, String content) {
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
