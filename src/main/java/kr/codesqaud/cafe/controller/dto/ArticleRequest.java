package kr.codesqaud.cafe.controller.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.article.Article;

public class ArticleRequest {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private final Long id;
	private final String writer;
	private final String title;
	private final String content;
	private final String createdAt;

	public ArticleRequest(Long id, String writer, String title, String content, LocalDateTime createdAt) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.createdAt = DATE_TIME_FORMATTER.format(createdAt);
	}

	public static ArticleRequest from(final Article article) {
		return new ArticleRequest(article.getId(), article.getWriter(), article.getTitle(), article.getContent(),
								  article.getCreatedAt());
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

	public String getCreatedAt() {
		return createdAt;
	}
}
