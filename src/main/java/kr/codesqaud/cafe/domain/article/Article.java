package kr.codesqaud.cafe.domain.article;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.controller.dto.req.PostingRequest;

public class Article {

	private Long id;
	private String writer;
	private String title;
	private String content;
	private LocalDateTime createdAt;

	private Article(String writer, String title, String content, LocalDateTime createdAt) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
	}

	public static Article from(final PostingRequest postingRequest) {
		return new Article(postingRequest.getWriter(),
			postingRequest.getTitle(),
			postingRequest.getContents(),
			LocalDateTime.now());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
