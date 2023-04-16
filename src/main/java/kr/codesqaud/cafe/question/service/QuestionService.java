package kr.codesqaud.cafe.question.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.common.domain.PageHandler;
import kr.codesqaud.cafe.question.dto.request.QuestionWriteRequestDTO;
import kr.codesqaud.cafe.question.dto.response.QuestionBoardResponseDTO;
import kr.codesqaud.cafe.question.dto.response.QuestionDetailResponseDTO;
import kr.codesqaud.cafe.question.dto.response.QuestionTitleResponseDTO;
import kr.codesqaud.cafe.question.repository.QuestionRepository;

@Service
public class QuestionService {
	private final QuestionRepository repository;

	public QuestionService(QuestionRepository repository) {
		this.repository = repository;
	}

	/**
	 * Q&A 게시글 저장하기
	 * @param dto Q&A 게시글 입력 정보
	 */
	public void addQuestion(QuestionWriteRequestDTO dto) {
		repository.insert(dto);
	}

	/**
	 * Q&A 게시판에 필요한 정보 만들기
	 * @param page 게시판 페이지 번호
	 * @return Q&A 게시글 목록과 페이징 정보를 담고 있는 dto
	 */
	public QuestionBoardResponseDTO makeQuestionBoard(int page) {
		PageHandler handler = new PageHandler(repository.countAll(), page);
		List<QuestionTitleResponseDTO> dto = repository.selectQuestionTitlesByOffset(handler.getPostOffset(),
			handler.getPageSize());
		return new QuestionBoardResponseDTO(handler, dto);
	}

	/**
	 * Q&A 게시글 상세정보 불러오기
	 * @param id 불러올 게시글의 id
	 * @return Q&A 게시글 상세정보
	 * @throws NoSuchElementException 없는 게시글의 idx를 조회한 경우 Exception 발생
	 */
	public QuestionDetailResponseDTO findQuestion(int id) throws NoSuchElementException {
		return repository.selectById(id);
	}
}
