package kr.codesqaud.cafe.controller.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.article.Article;

public class ArticleDto {

	private final String writer;
	private final String title;
	private final String createdAt;

	private ArticleDto(String writer, String title, LocalDateTime createdAt) {
		this.writer = writer;
		this.title = title;
		this.createdAt = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm").format(createdAt);
	}

	public static ArticleDto from(Article article) {
		return new ArticleDto(article.getWriter(), article.getTitle(), article.getCreatedAt());
	}
}
