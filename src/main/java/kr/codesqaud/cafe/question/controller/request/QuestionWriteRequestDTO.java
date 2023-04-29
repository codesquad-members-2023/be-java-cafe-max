package kr.codesqaud.cafe.question.controller.request;

import kr.codesqaud.cafe.question.domain.QuestionEntity;

public class QuestionWriteRequestDTO {
	private final String title;
	private final String contents;

	public QuestionWriteRequestDTO(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public QuestionEntity toEntity(long writer_id) {
		return new QuestionEntity(writer_id, title, contents);
	}

	public QuestionEntity toEntity(long id, long writer_id) {
		return new QuestionEntity(id, writer_id, title, contents);
	}
}
