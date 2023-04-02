package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

public class PostingRequest {
	private final String writer;
	private final String title;
	private final String contents;

	public PostingRequest(String writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public Article getArticleEntity() {
		return new Article(getWriter(), getTitle(), getContents());
	}
}
