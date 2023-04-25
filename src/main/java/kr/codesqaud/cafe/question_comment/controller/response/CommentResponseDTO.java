package kr.codesqaud.cafe.question_comment.controller.response;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.question_comment.domain.CommentEntity;

public class CommentResponseDTO {
	private long id;
	private long post_id;
	private long writer_id;
	private String writer;
	private String content;
	private boolean is_deleted;
	private LocalDateTime registrationDateTime;

	public CommentResponseDTO(long id, long post_id, long writer_id, String writer, String content,
		boolean is_deleted,
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

	public static CommentResponseDTO from(CommentEntity comment) {
		return new CommentResponseDTO(comment.getId(), comment.getPost_id(), comment.getWriter_id(),
			comment.getWriter(),
			comment.getContent(),
			comment.isIs_deleted(),
			comment.getRegistrationDateTime());
	}
}
