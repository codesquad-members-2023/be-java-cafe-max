package kr.codesqaud.cafe.controller.dto;

import java.util.List;

public class ArticleDetails {

	private final ArticleRequest articleRequest;
	private final List<ArticleCommentRequest> articleCommentRequest;

	public ArticleDetails(ArticleRequest articleRequest, List<ArticleCommentRequest> articleCommentRequest) {
		this.articleRequest = articleRequest;
		this.articleCommentRequest = articleCommentRequest;
	}

	public ArticleRequest getArticleRequest() {
		return articleRequest;
	}

	public List<ArticleCommentRequest> getArticleCommentRequest() {
		return articleCommentRequest;
	}
}
