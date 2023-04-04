package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;

/**
 * Q&A 게시판 목록 페이지에 보여줄 게시글 목록 중 하나의 title 정보를 저장할 DTO
 */
public class QuestionTitleDTO {
	private int idx;
	private String writer;
	private String title;
	private LocalDateTime registrationDate;

	public QuestionTitleDTO(int idx, String writer, String title, LocalDateTime registrationDate) {
		this.idx = idx;
		this.writer = writer;
		this.title = title;
		this.registrationDate = registrationDate;
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

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}
}
