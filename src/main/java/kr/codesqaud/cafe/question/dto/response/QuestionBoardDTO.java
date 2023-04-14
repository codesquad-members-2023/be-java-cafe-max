package kr.codesqaud.cafe.question.dto.response;

import java.util.List;

import kr.codesqaud.cafe.common.domain.PageHandler;

/**
 * Q&A 게시판 목록과 페이징 정보를 저장할 DTO
 */
public class QuestionBoardDTO {
	private final PageHandler pageHandler;
	private final List<QuestionTitleDTO> questionTitles;

	public QuestionBoardDTO(PageHandler pageHandler, List<QuestionTitleDTO> questionTitles) {
		this.pageHandler = pageHandler;
		this.questionTitles = questionTitles;
	}

	public PageHandler getPageHandler() {
		return pageHandler;
	}

	public List<QuestionTitleDTO> getQuestionTitles() {
		return questionTitles;
	}
}
