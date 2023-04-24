package kr.codesqaud.cafe.controller.dto;

import java.util.List;

public class ArticleDetails {

	private final ArticleResponse articleResponse;
	private final List<CommentResponse> commentResponse;

	public ArticleDetails(ArticleResponse articleResponse, List<CommentResponse> commentResponse) {
		this.articleResponse = articleResponse;
		this.commentResponse = commentResponse;
	}

	public ArticleResponse getArticleResponse() {
		return articleResponse;
	}

	public List<CommentResponse> getCommentResponse() {
		return commentResponse;
	}
}
