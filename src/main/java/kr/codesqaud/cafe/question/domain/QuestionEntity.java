package kr.codesqaud.cafe.question.domain;

import java.time.LocalDateTime;

public class QuestionEntity {
	private long id;
	private final long writer_id;
	private String writer;
	private String title;
	private String contents;
	private boolean is_deleted;
	private LocalDateTime registrationDateTime;

	public QuestionEntity(long writer_id, String title, String contents) {
		this.writer_id = writer_id;
		this.title = title;
		this.contents = contents;
	}

	public QuestionEntity(long id, long writer_id, String title, String contents) {
		this.id = id;
		this.writer_id = writer_id;
		this.title = title;
		this.contents = contents;
	}

	public QuestionEntity(long id, long writer_id, String writer, String title, String contents
		, boolean is_deleted, LocalDateTime registrationDateTime) {
		this.id = id;
		this.writer_id = writer_id;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.is_deleted = is_deleted;
		this.registrationDateTime = registrationDateTime;
	}

	public long getId() {
		return id;
	}

	public long getWriter_id() {
		return writer_id;
	}

	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public LocalDateTime getRegistrationDateTime() {
		return registrationDateTime;
	}

	public void updateFrom(QuestionEntity question) {
		this.title = question.getTitle();
		this.contents = question.getContents();
	}

	public void updateToDeleteState() {
		this.is_deleted = false;
	}
}
