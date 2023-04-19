package kr.codesqaud.cafe.domain.article.entity;

import java.time.LocalDateTime;

public class Article {
	private Long id;
	private String content;
	private String title;
	private String writer;
	private LocalDateTime dateTime;

	public Article(String title, String content, String writer) {
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.dateTime = LocalDateTime.now();
	}

	public Article() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}

