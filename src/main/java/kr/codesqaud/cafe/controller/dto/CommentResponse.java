package kr.codesqaud.cafe.controller.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.comment.Comment;

public class CommentResponse {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private final Long id;
	private final String writer;
	private final String content;
	private final String createdAt;

	public CommentResponse(Long id, String writer, String content, LocalDateTime createdAt) {
		this.id = id;
		this.writer = writer;
		this.content = content;
		this.createdAt = DATE_TIME_FORMATTER.format(createdAt);
	}

	public static CommentResponse from(final Comment comment) {
		return new CommentResponse(
			comment.getId(),
			comment.getWriter(),
			comment.getContent(),
			comment.getCreatedAt()
		);
	}
}
