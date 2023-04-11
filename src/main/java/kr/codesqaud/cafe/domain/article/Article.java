package kr.codesqaud.cafe.domain.article;

import java.time.LocalDateTime;

public class Article {

	private Long id;
	private String writer;
	private String title;
	private String content;
	private LocalDateTime createdAt;

	public Article(Long id, String writer, String title, String content, LocalDateTime createdAt) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
