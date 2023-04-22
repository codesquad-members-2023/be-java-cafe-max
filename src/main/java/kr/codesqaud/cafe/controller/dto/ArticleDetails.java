package kr.codesqaud.cafe.controller.dto;

import java.util.List;

public class ArticleDetails {

	private final ArticleResponse articleResponse;
	private final List<ArticleCommentResponse> articleCommentResponse;

	public ArticleDetails(ArticleResponse articleResponse, List<ArticleCommentResponse> articleCommentResponse) {
		this.articleResponse = articleResponse;
		this.articleCommentResponse = articleCommentResponse;
	}

	public ArticleResponse getArticleResponse() {
		return articleResponse;
	}

	public List<ArticleCommentResponse> getArticleCommentResponse() {
		return articleCommentResponse;
	}
}
