package kr.codesqaud.cafe.question.domain;

import java.time.LocalDateTime;

public class Question {
	private long id;
	private String writer;
	private String title;
	private String contents;
	private LocalDateTime registrationDateTime;

	public Question(String writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	public Question(long id, String writer, String title, String contents, LocalDateTime registrationDateTime) {
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
