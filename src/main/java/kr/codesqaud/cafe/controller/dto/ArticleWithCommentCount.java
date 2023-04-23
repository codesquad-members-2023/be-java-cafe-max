package kr.codesqaud.cafe.controller.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleWithCommentCount {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private final Long id;
	private final String writer;
	private final String title;
	private final String content;
	private final String createdAt;
	private final Long commentCount;

	public ArticleWithCommentCount(Long id, String writer, String title, String content, LocalDateTime createdAt,
	                               Long commentCount) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.createdAt = DATE_TIME_FORMATTER.format(createdAt);
		this.commentCount = commentCount;
	}
}
