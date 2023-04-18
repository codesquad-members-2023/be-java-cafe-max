package kr.codesqaud.cafe.question.dto.response;

import java.util.List;

import kr.codesqaud.cafe.common.web.PageHandler;

/**
 * Q&A 게시판 목록과 페이징 정보를 저장할 DTO
 */
public class QuestionBoardResponseDTO {
	private final PageHandler pageHandler;
	private final List<QuestionTitleResponseDTO> questionTitles;

	public QuestionBoardResponseDTO(PageHandler pageHandler, List<QuestionTitleResponseDTO> questionTitles) {
		this.pageHandler = pageHandler;
		this.questionTitles = questionTitles;
	}

	public PageHandler getPageHandler() {
		return pageHandler;
	}

	public List<QuestionTitleResponseDTO> getQuestionTitles() {
		return questionTitles;
	}
}
