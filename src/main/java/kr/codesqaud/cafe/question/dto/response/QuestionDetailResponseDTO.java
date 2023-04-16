package kr.codesqaud.cafe.question.dto.response;

import java.time.LocalDateTime;

/**
 * Q&A 게시판 글 하나의 디테일한 정보를 저장할 DTO
 */
public class QuestionDetailResponseDTO {
	private final int id;
	private final String writer;
	private final String title;
	private final String contents;
	private final LocalDateTime registrationDateTime;

	public QuestionDetailResponseDTO(int id, String writer, String title, String contents,
		LocalDateTime registrationDateTime) {
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
}
