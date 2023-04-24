package kr.codesqaud.cafe.controller.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.comment.Comment;

public class CommentSaveResponse {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private Long id;
	private String content;
	private String createdAt;
	private String writer;
	private Long articleId;

	public CommentSaveResponse() {
	}

	public CommentSaveResponse(Long id, String content, LocalDateTime createdAt, String writer, Long articleId) {
		this.id = id;
		this.content = content;
		this.createdAt = DATE_TIME_FORMATTER.format(createdAt);
		this.writer = writer;
		this.articleId = articleId;
	}

	public static CommentSaveResponse from(final Comment comment) {
		return new CommentSaveResponse(
			comment.getId(),
			comment.getContent(),
			comment.getCreatedAt(),
			comment.getWriter(),
			comment.getArticleId()
		);
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getWriter() {
		return writer;
	}

	public Long getArticleId() {
		return articleId;
	}
}
