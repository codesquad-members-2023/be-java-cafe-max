package kr.codesqaud.cafe.question.dto.response;

import java.time.LocalDateTime;

/**
 * Q&A 게시판 목록 페이지에 보여줄 게시글 목록 중 하나의 title 정보를 저장할 DTO
 */
public class QuestionTitleResponseDTO {
	private final int id;
	private final String writer;
	private final String title;
	private final LocalDateTime registrationDateTime;

	public QuestionTitleResponseDTO(int id, String writer, String title, LocalDateTime registrationDateTime) {
		this.id = id;
		this.writer = writer;
		this.title = title;
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

	public LocalDateTime getRegistrationDateTime() {
		return registrationDateTime;
	}
}
