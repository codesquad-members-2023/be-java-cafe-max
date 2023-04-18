package kr.codesqaud.cafe.controller.dto.req;

import kr.codesqaud.cafe.domain.articlecomment.ArticleComment;

public class ReplyRequest {

	private final Long articleId;
	private final String content;

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

	public ArticleComment toEntity(final String userId) {
		return ArticleComment.of(content, userId, articleId);
	}
}
