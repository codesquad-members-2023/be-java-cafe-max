package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.domain.Question;

public class QuestionWriteDTO {
	private String writer;
	private String title;
	private String contents;

	public QuestionWriteDTO(String writer, String title, String contents) {
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

	public Question toEntity(int idx) {
		return new Question(idx, writer, title, contents, LocalDateTime.now());
	}
}
