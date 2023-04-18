package kr.codesqaud.cafe.question.repository;

import java.util.List;
import java.util.NoSuchElementException;

import kr.codesqaud.cafe.question.domain.Question;

public interface QuestionRepository {
	void save(Question question);

	long countBy();

	List<Question> findAll(long offset, int pageSize);

	Question findById(long id) throws NoSuchElementException;
}
