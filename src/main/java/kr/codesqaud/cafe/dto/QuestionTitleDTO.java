package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;

/**
 * Q&A 게시판 목록 페이지에 보여줄 게시글 목록 중 하나의 title 정보를 저장할 DTO
 */
public class QuestionTitleDTO {
	private final int idx;
	private final String writer;
	private final String title;
	private final LocalDateTime registrationDateTime;

	public QuestionTitleDTO(int idx, String writer, String title, LocalDateTime registrationDateTime) {
		this.idx = idx;
		this.writer = writer;
		this.title = title;
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

	public LocalDateTime getRegistrationDateTime() {
		return registrationDateTime;
	}
}
