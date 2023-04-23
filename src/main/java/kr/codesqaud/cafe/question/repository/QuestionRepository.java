package kr.codesqaud.cafe.question.repository;

import java.util.List;

import kr.codesqaud.cafe.question.domain.QuestionEntity;
import kr.codesqaud.cafe.question.exception.QuestionNotExistException;

public interface QuestionRepository {
	void save(QuestionEntity question);

	long countBy();

	List<QuestionEntity> findPageBy(long offset, int pageSize);

	QuestionEntity findById(long id) throws QuestionNotExistException;
}
