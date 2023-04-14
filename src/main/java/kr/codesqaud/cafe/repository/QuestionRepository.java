package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.NoSuchElementException;

import kr.codesqaud.cafe.dto.QuestionDetailDTO;
import kr.codesqaud.cafe.dto.QuestionTitleDTO;
import kr.codesqaud.cafe.dto.QuestionWriteDTO;

public interface QuestionRepository {
	void insert(QuestionWriteDTO dto);

	int countAll();

	List<QuestionTitleDTO> selectQuestionTitlesByOffset(int postOffset, int pageSize);

	QuestionDetailDTO selectByIdx(int idx) throws NoSuchElementException;
}
