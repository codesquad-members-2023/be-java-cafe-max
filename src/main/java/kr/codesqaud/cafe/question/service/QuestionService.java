package kr.codesqaud.cafe.question.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.question.domain.Question;
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

	public List<Question> findAll(long offset, int pageSize) {
		return repository.findAll(offset, pageSize);
	}

	public Question findById(long id) throws NoSuchElementException {
		return repository.findById(id);
	}
}
