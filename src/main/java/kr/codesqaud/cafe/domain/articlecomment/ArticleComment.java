package kr.codesqaud.cafe.domain.articlecomment;

import java.time.LocalDateTime;

public class ArticleComment {

	private Long id;
	private String content;
	private LocalDateTime createdAt;
	private Boolean isDeleted;
	private String writer;
	private Long articleId;

	public ArticleComment(Long id, String content, LocalDateTime createdAt, String writer, Long articleId) {
		this.id = id;
		this.content = content;
		this.createdAt = createdAt;
		this.isDeleted = Boolean.FALSE;
		this.writer = writer;
		this.articleId = articleId;
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public Boolean getDeleted() {
		return isDeleted;
	}

	public String getWriter() {
		return writer;
	}

	public Long getArticleId() {
		return articleId;
	}
}
