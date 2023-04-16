package kr.codesqaud.cafe.question.domain;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.question.dto.response.QuestionDetailDTO;
import kr.codesqaud.cafe.question.dto.response.QuestionTitleDTO;

public class Question {
	private int idx;
	private String writer;
	private String title;
	private String contents;
	private LocalDateTime registrationDateTime;

	public Question(int idx, String writer, String title, String contents, LocalDateTime registrationDateTime) {
		this.idx = idx;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.registrationDateTime = registrationDateTime;
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

	public LocalDateTime getRegistrationDateTime() {
		return registrationDateTime;
	}

	public QuestionDetailDTO toDetailsDto() {
		return new QuestionDetailDTO(idx, writer, title, contents, registrationDateTime);
	}

	public QuestionTitleDTO toTitleDto() {
		return new QuestionTitleDTO(idx, writer, title, registrationDateTime);
	}
}
