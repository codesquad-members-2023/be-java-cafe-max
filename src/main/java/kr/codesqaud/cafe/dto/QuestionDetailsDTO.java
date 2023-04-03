package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;

public class QuestionDetailsDTO {
	private int idx;
	private String writer;
	private String title;
	private String contents;
	private LocalDateTime registrationDate;

	public QuestionDetailsDTO(int idx, String writer, String title, String contents, LocalDateTime registrationDate) {
		this.idx = idx;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.registrationDate = registrationDate;
	}

	public int getIdx() {
		return idx;
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

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}
}
