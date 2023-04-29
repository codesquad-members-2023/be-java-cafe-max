package kr.codesqaud.cafe.question.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.question.domain.QuestionEntity;

public interface QuestionRepository {
	void save(QuestionEntity question);

	long countBy();

	List<QuestionEntity> findPageBy(long offset, int pageSize);

	Optional<QuestionEntity> findById(long id);

	boolean update(QuestionEntity question);

	boolean delete(long id);
}
