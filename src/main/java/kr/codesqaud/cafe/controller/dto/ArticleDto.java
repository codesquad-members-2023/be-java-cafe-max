package kr.codesqaud.cafe.controller.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.Article;

public class ArticleDto {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

	private final Long id;
	private final String writer;
	private final String title;
	private final String contents;
	private final String createAt;

	public ArticleDto(Long id, String writer, String title, String contents, LocalDateTime createAt) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createAt = FORMATTER.format(createAt);
	}

	public static ArticleDto fromEntity(Article article) {
		return new ArticleDto(
			article.getId(),
			article.getWriter(),
			article.getTitle(),
			article.getContents(),
			article.getCreateAt()
		);
	}

	public String getWriter() {
		return writer;
	}
}
