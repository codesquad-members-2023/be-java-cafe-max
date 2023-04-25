package kr.codesqaud.cafe.question.controller.response;

import java.time.LocalDateTime;
import java.util.List;

import kr.codesqaud.cafe.question.domain.QuestionEntity;
import kr.codesqaud.cafe.question_comment.controller.response.CommentResponseDTO;

/**
 * Q&A 게시판 글 하나의 디테일한 정보를 저장할 DTO
 */
public class QuestionDetailResponseDTO {
	private final long id;
	private final long writer_id;
	private final String writer;
	private final String title;
	private final String contents;
	private final LocalDateTime registrationDateTime;

	private final List<CommentResponseDTO> comments;

	public QuestionDetailResponseDTO(long id, long writer_id, String writer, String title, String contents,
		LocalDateTime registrationDateTime, List<CommentResponseDTO> comments) {
		this.id = id;
		this.writer_id = writer_id;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.registrationDateTime = registrationDateTime;
		this.comments = comments;
	}

	public long getId() {
		return id;
	}

	public long getWriter_id() {
		return writer_id;
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

	public List<CommentResponseDTO> getComments() {
		return comments;
	}

	public static QuestionDetailResponseDTO from(QuestionEntity question, List<CommentResponseDTO> comments) {
		return new QuestionDetailResponseDTO(question.getId(), question.getWriter_id(), question.getWriter(),
			question.getTitle(),
			question.getContents(),
			question.getRegistrationDateTime(),
			comments);
	}
}
