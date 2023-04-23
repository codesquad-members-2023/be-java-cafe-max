package kr.codesqaud.cafe.domain.article.dto.request;

import kr.codesqaud.cafe.domain.article.entity.Article;

public class ArticleSaveRequestDto {
	private String title;
	private String content;

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public ArticleSaveRequestDto(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public Article toEntity(String writer) {
		return new Article(title, content, writer);
	}
}
