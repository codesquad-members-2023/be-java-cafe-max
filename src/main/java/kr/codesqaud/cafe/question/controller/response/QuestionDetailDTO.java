package kr.codesqaud.cafe.question.controller.response;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.question.domain.QuestionEntity;

/**
 * Q&A 게시판 글 하나의 디테일한 정보를 저장할 DTO
 */
public class QuestionDetailDTO {
	private final long id;
	private final String writer;
	private final String title;
	private final String contents;
	private final LocalDateTime registrationDateTime;

	public QuestionDetailDTO(long id, String writer, String title, String contents,
		LocalDateTime registrationDateTime) {
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

	public static QuestionDetailDTO from(QuestionEntity question) {
		return new QuestionDetailDTO(question.getId(), question.getWriter(), question.getTitle(),
			question.getContents(),
			question.getRegistrationDateTime());
	}
}
