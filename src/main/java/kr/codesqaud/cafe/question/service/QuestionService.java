package kr.codesqaud.cafe.question.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.question.domain.Question;
import kr.codesqaud.cafe.question.exception.QuestionNotExistException;
import kr.codesqaud.cafe.question.repository.QuestionRepository;

@Service
public class QuestionService {
	private final QuestionRepository repository;

	public QuestionService(QuestionRepository repository) {
		this.repository = repository;
	}

	public void save(Question question) {
		repository.save(question);
	}

	public long countBy() {
		return repository.countBy();
	}

	public List<Question> findPageBy(long offset, int pageSize) {
		return repository.findPageBy(offset, pageSize);
	}

	public Question findById(long id) throws QuestionNotExistException {
		return repository.findById(id);
	}
}
