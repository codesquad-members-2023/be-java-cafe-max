package kr.codesqaud.cafe.question.controller.response;

import java.util.List;

import kr.codesqaud.cafe.common.web.PageHandler;

/**
 * Q&A 게시판 목록과 페이징 정보를 저장할 DTO
 */
public class QuestionBoardResponseDTO {
	private final List<QuestionTitleResponseDTO> questionTitles;

	private final boolean previousBlock;
	private final boolean nextBlock;
	private final long previousPage;
	private final long nextPage;
	private final List<Long> pagingBar;

	public QuestionBoardResponseDTO(List<QuestionTitleResponseDTO> questionTitles, PageHandler pageHandler) {
		this.questionTitles = questionTitles;

		this.previousBlock = pageHandler.isPreviousBlock();
		this.nextBlock = pageHandler.isNextBlock();
		this.previousPage = pageHandler.getPreviousPage();
		this.nextPage = pageHandler.getNextPage();
		this.pagingBar = pageHandler.getPagingBar();

	}

	public List<QuestionTitleResponseDTO> getQuestionTitles() {
		return questionTitles;
	}

	public boolean isPreviousBlock() {
		return previousBlock;
	}

	public boolean isNextBlock() {
		return nextBlock;
	}

	public long getPreviousPage() {
		return previousPage;
	}

	public long getNextPage() {
		return nextPage;
	}

	public List<Long> getPagingBar() {
		return pagingBar;
	}
}
