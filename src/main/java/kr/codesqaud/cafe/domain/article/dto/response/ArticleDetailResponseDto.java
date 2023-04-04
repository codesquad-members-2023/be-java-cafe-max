package kr.codesqaud.cafe.domain.article.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.article.entity.Article;

public class ArticleDetailResponseDto {
	String id;
	String content;
	String title;
	String dateTime;

	public ArticleDetailResponseDto(Article article) {
		this.title = article.getTitle();
		this.content = article.getContent();
		this.id = String.valueOf(article.getId());
		this.dateTime = setDateTime(article.getDateTime());
	}

	private String setDateTime(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E) HH:mm:ss"));
	}

	public String getId() {
		return id;
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
}
