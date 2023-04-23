package kr.codesqaud.cafe.domain.article.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.article.entity.Article;

public class ArticleDetailResponseDto {
	private String articleId;
	private String content;
	private String title;
	private String dateTime;
	private String writer;

	public ArticleDetailResponseDto(Article article) {
		this.title = article.getTitle();
		this.content = article.getContent();
		this.articleId = String.valueOf(article.getId());
		this.dateTime = setDateTime(article.getDateTime());
		this.writer = article.getWriter();
	}

	private String setDateTime(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public String getArticleId() {
		return articleId;
	}

	public String getContent() {
		return content;
	}

	public String getTitle() {
		return title;
	}

	public String getDateTime() {
		return dateTime;
	}

	public String getWriter() {
		return writer;
	}
}
