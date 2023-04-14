package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

public class PostingRequest {

	private final String title;
	private final String contents;

	public PostingRequest(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}

	public Article getArticleEntity(String writer) {
		return new Article(writer, title, contents);
	}
}
