package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Comment {
	private Long id;
	private String userId;
	private String contents;
	private LocalDateTime createdAt;
	private Long articleId;

	public Comment(String userId, String contents, Long articleId) {
		this(null, userId, contents, LocalDateTime.now(), articleId);
	}

	public Comment(Long id, String userId, String contents, LocalDateTime createdAt, Long articleId) {
		this.id = id;
		this.userId = userId;
		this.contents = contents;
		this.createdAt = createdAt;
		this.articleId = articleId;
	}

	public Long getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getContents() {
		return contents;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public Long getArticleId() {
		return articleId;
	}
}
