package kr.codesqaud.cafe.question_comment.domain;

import java.time.LocalDateTime;

public class CommentEntity {
	private long id;
	private long post_id;
	private long writer_id;
	private String writer;
	private String content;
	private boolean is_deleted;
	private LocalDateTime registrationDateTime;

	public CommentEntity(long post_id, long writer_id, String writer, String content) {
		this.post_id = post_id;
		this.writer_id = writer_id;
		this.writer = writer;
		this.content = content;
	}

	public CommentEntity(long id, long post_id, long writer_id, String writer, String content, boolean is_deleted,
		LocalDateTime registrationDateTime) {
		this.id = id;
		this.post_id = post_id;
		this.writer_id = writer_id;
		this.writer = writer;
		this.content = content;
		this.is_deleted = is_deleted;
		this.registrationDateTime = registrationDateTime;
	}

	public long getId() {
		return id;
	}

	public long getPost_id() {
		return post_id;
	}

	public long getWriter_id() {
		return writer_id;
	}

	public String getWriter() {
		return writer;
	}

	public String getContent() {
		return content;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public LocalDateTime getRegistrationDateTime() {
		return registrationDateTime;
	}
}
