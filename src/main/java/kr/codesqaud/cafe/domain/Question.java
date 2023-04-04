package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.dto.QuestionDetailsDTO;
import kr.codesqaud.cafe.dto.QuestionTitleDTO;

public class Question {
	private int idx;
	private String writer;
	private String title;
	private String contents;
	private LocalDateTime registrationDate;

	public Question(int idx, String writer, String title, String contents, LocalDateTime registrationDate) {
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

	public QuestionDetailsDTO toDetailsDto() {
		return new QuestionDetailsDTO(idx, writer, title, contents, registrationDate);
	}

	public QuestionTitleDTO toTitleDto() {
		return new QuestionTitleDTO(idx, writer, title, registrationDate);
	}
}
