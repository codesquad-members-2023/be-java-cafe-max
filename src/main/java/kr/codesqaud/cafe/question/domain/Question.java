package kr.codesqaud.cafe.question.domain;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.question.dto.response.QuestionDetailResponseDTO;
import kr.codesqaud.cafe.question.dto.response.QuestionTitleResponseDTO;

public class Question {
	private int id;
	private String writer;
	private String title;
	private String contents;
	private LocalDateTime registrationDateTime;

	public Question(int id, String writer, String title, String contents, LocalDateTime registrationDateTime) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.registrationDateTime = registrationDateTime;
	}

	public int getId() {
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

	public QuestionDetailResponseDTO toDetailsDto() {
		return new QuestionDetailResponseDTO(id, writer, title, contents, registrationDateTime);
	}

	public QuestionTitleResponseDTO toTitleDto() {
		return new QuestionTitleResponseDTO(id, writer, title, registrationDateTime);
	}
}
