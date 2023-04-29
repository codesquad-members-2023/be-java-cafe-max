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

	/**
	 * Q&A 게시글 수정 기능
	 * @param question 수정할 게시글 정보
	 * @throws QuestionNotExistException 게시글이 존재하지 않을 경우(삭제된 경우) 예외 발생
	 */
	public void update(QuestionEntity question) throws QuestionNotExistException {
		if (repository.update(question)) {
			return;
		}
		throw new QuestionNotExistException(question.getId());
	}

	/**
	 * Q&A 게시글 삭제 기능
	 * @param id 삭제할 게시글의 id
	 * @throws QuestionNotExistException 게시글이 존재하지 않을 경우(삭제된 경우) 예외 발생
	 */
	public void delete(long id) throws QuestionNotExistException {
		if (repository.delete(id)) {
			return;
		}
		throw new QuestionNotExistException(id);
	}
}
