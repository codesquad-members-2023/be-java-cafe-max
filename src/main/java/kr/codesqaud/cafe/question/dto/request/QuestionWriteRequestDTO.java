package kr.codesqaud.cafe.question.dto.request;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.question.domain.Question;

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

	public Question toEntity(int id) {
		return new Question(id, writer, title, contents, LocalDateTime.now());
	}
}
