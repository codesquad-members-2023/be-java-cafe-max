package kr.codesqaud.cafe.question.controller.request;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.question.domain.QuestionEntity;

public class QuestionWriteRequestDTO {
	private final String writer;
	private final String title;
	private final String contents;

	public QuestionWriteRequestDTO(String writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
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

	public QuestionEntity toEntity() {
		return new QuestionEntity(-1, writer, title, contents, LocalDateTime.now());
	}
}
