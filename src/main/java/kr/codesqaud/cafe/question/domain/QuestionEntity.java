package kr.codesqaud.cafe.question.domain;

import java.time.LocalDateTime;

public class QuestionEntity {
	private long id;
	private String writer;
	private String title;
	private String contents;
	private LocalDateTime registrationDateTime;

	public QuestionEntity(String writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	public QuestionEntity(long id, String writer, String title, String contents, LocalDateTime registrationDateTime) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.registrationDateTime = registrationDateTime;
	}

	public long getId() {
		return id;
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

	public LocalDateTime getRegistrationDateTime() {
		return registrationDateTime;
	}
}
