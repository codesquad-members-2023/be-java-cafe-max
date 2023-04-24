package kr.codesqaud.cafe.domain.comment;

import java.time.LocalDateTime;

public class Comment {

	private Long id;
	private String content;
	private LocalDateTime createdAt;
	private Boolean isDeleted;
	private String writer;
	private Long articleId;

	public Comment(Long id, String content, LocalDateTime createdAt, String writer, Long articleId) {
		this.id = id;
		this.content = content;
		this.createdAt = createdAt;
		this.isDeleted = Boolean.FALSE;
		this.writer = writer;
		this.articleId = articleId;
	}

	public static Comment of(final String content, final String writer, Long articleId) {
		return new Comment(null, content, LocalDateTime.now(), writer, articleId);
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
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

	public boolean isSameWriter(final String userId) {
		return writer.equals(userId);
	}
}
