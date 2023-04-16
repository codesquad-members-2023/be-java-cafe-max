package kr.codesqaud.cafe.controller.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.article.Article;

public class ArticleParam {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private final Long id;
	private final String writer;
	private final String title;
	private final String content;
	private final String createdAt;

	public ArticleParam(Long id, String writer, String title, String content, LocalDateTime createdAt) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.createdAt = DATE_TIME_FORMATTER.format(createdAt);
	}

	public Article toEntity() {
		return new Article(
			id,
			writer,
			title,
			content,
			LocalDateTime.parse(createdAt, DATE_TIME_FORMATTER)
		);
	}

	public static ArticleParam from(final Article article) {
		return new ArticleParam(article.getId(), article.getWriter(), article.getTitle(), article.getContent(),
								article.getCreatedAt());
	}
}
