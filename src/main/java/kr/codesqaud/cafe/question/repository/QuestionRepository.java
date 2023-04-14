package kr.codesqaud.cafe.question.repository;

import java.util.List;
import java.util.NoSuchElementException;

import kr.codesqaud.cafe.question.dto.response.QuestionDetailDTO;
import kr.codesqaud.cafe.question.dto.response.QuestionTitleDTO;
import kr.codesqaud.cafe.question.dto.response.QuestionWriteDTO;

public interface QuestionRepository {
	void insert(QuestionWriteDTO dto);

	int countAll();

	List<QuestionTitleDTO> selectQuestionTitlesByOffset(int postOffset, int pageSize);

	QuestionDetailDTO selectByIdx(int idx) throws NoSuchElementException;
}
