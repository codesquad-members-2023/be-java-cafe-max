package kr.codesqaud.cafe.controller.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.Comment;

public class CommentDto {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private final Long id;
	private final String userId;
	private final String contents;
	private final String createdAt;
	private final Long articleId;

	private CommentDto(Long id, String userId, String contents, LocalDateTime createdAt, Long articleId) {
		this.id = id;
		this.userId = userId;
		this.contents = contents;
		this.createdAt = FORMATTER.format(createdAt);
		this.articleId = articleId;
	}

	public static CommentDto fromEntity(Comment comment) {
		return new CommentDto(
			comment.getId(),
			comment.getUserId(),
			comment.getContents(),
			comment.getCreatedAt(),
			comment.getArticleId()
		);
	}

}
