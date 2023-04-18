package kr.codesqaud.cafe.question.controller.response;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.question.domain.Question;

/**
 * Q&A 게시판 목록 페이지에 보여줄 게시글 목록 중 하나의 title 정보를 저장할 DTO
 */
public class QuestionTitleResponseDTO {
	private final long id;
	private final String writer;
	private final String title;
	private final LocalDateTime registrationDateTime;

	public QuestionTitleResponseDTO(long id, String writer, String title, LocalDateTime registrationDateTime) {
		this.id = id;
		this.writer = writer;
		this.title = title;
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

	public LocalDateTime getRegistrationDateTime() {
		return registrationDateTime;
	}

	public static QuestionTitleResponseDTO from(Question question) {
		return new QuestionTitleResponseDTO(question.getId(), question.getWriter(), question.getTitle(),
			question.getRegistrationDateTime());
	}
}
