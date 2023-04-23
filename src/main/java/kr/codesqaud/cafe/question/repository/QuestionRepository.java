package kr.codesqaud.cafe.question.repository;

import java.util.List;

import kr.codesqaud.cafe.question.domain.Question;
import kr.codesqaud.cafe.question.exception.QuestionNotExistException;

public interface QuestionRepository {
	void save(Question question);

	long countBy();

	List<Question> findPageBy(long offset, int pageSize);

	Question findById(long id) throws QuestionNotExistException;
}
