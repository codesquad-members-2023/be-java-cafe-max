package kr.codesqaud.cafe.domain.article.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
	Long id;
	String content;
	String title;
	String dateTime;

	public Article(String title, String content) {
		this.title = title;
		this.content = content;
		this.dateTime = setDateTime();
	}

	public String setDateTime() {
		LocalDateTime now = LocalDateTime.now();
		return now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E) HH:mm:ss"));
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

