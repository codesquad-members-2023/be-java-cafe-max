package kr.codesqaud.cafe.question.repository;

import java.util.List;
import java.util.NoSuchElementException;

import kr.codesqaud.cafe.question.dto.request.QuestionWriteRequestDTO;
import kr.codesqaud.cafe.question.dto.response.QuestionDetailResponseDTO;
import kr.codesqaud.cafe.question.dto.response.QuestionTitleResponseDTO;

public interface QuestionRepository {
	void insert(QuestionWriteRequestDTO dto);

	int countAll();

	List<QuestionTitleResponseDTO> selectQuestionTitlesByOffset(int postOffset, int pageSize);

	QuestionDetailResponseDTO selectById(int id) throws NoSuchElementException;
}
