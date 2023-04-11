package kr.codesqaud.cafe.service;

import java.util.NoSuchElementException;

import kr.codesqaud.cafe.dto.QuestionBoardDTO;
import kr.codesqaud.cafe.dto.QuestionDetailDTO;
import kr.codesqaud.cafe.dto.QuestionWriteDTO;

public interface QuestionService {

	/**
	 * Q&A 게시글 저장하기
	 * @param dto Q&A 게시글 입력 정보
	 */
	void addQuestion(QuestionWriteDTO dto);

	/**
	 * Q&A 게시판에 필요한 정보 만들기
	 * @param page 게시판 페이지 번호
	 * @return Q&A 게시글 목록과 페이징 정보를 담고 있는 dto
	 */
	QuestionBoardDTO makeQuestionBoard(int page);

	/**
	 * Q&A 게시글 상세정보 불러오기
	 * @param idx 불러올 게시글의 idx
	 * @return Q&A 게시글 상세정보
	 * @throws NoSuchElementException 없는 게시글의 idx를 조회한 경우 Exception 발생
	 */
	QuestionDetailDTO findQuestion(int idx) throws NoSuchElementException;
}
