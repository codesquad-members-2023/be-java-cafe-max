package kr.codesqaud.cafe.question.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.question.domain.QuestionEntity;
import kr.codesqaud.cafe.question.exception.QuestionNotExistException;
import kr.codesqaud.cafe.question.repository.QuestionRepository;

@Service
public class QuestionService {
	private final QuestionRepository repository;

	public QuestionService(QuestionRepository repository) {
		this.repository = repository;
	}

	public void save(QuestionEntity question) {
		repository.save(question);
	}

	public long countBy() {
		return repository.countBy();
	}

	public List<QuestionEntity> findPageBy(long offset, int pageSize) {
		return repository.findPageBy(offset, pageSize);
	}

	public QuestionEntity findById(long id) throws QuestionNotExistException {
		Optional<QuestionEntity> questionToFind = repository.findById(id);

		if (questionToFind.isPresent()) {
			return questionToFind.get();
		} else {
			throw new QuestionNotExistException(id);
		}
	}
}
