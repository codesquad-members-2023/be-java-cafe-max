package kr.codesqaud.cafe.domain.article.entity;

import java.time.LocalDateTime;

public class Article {
	Long id;
	String content;
	String title;
	LocalDateTime dateTime;

	public Article(String title, String content) {
		this.title = title;
		this.content = content;
		this.dateTime = LocalDateTime.now();
	}

	public Article() {
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}
}

