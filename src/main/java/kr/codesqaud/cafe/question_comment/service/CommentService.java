package kr.codesqaud.cafe.question_comment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.question_comment.domain.CommentEntity;
import kr.codesqaud.cafe.question_comment.exception.CommentNotExistException;
import kr.codesqaud.cafe.question_comment.repository.CommentRepository;

@Service
public class CommentService {
	private final CommentRepository repository;

	public CommentService(CommentRepository repository) {
		this.repository = repository;
	}

	public void save(CommentEntity comment) {
		repository.save(comment);
	}

	public List<CommentEntity> findByPostId(long post_id) {
		return repository.findByPostId(post_id);
	}

	public CommentEntity findById(long id) throws CommentNotExistException {
		Optional<CommentEntity> commentToFind = repository.findById(id);

		if (commentToFind.isPresent()) {
			return commentToFind.get();
		} else {
			throw new CommentNotExistException(id);
		}
	}

	public void deleteById(long id) throws CommentNotExistException {
		if (repository.deleteById(id)) {
			return;
		}
		throw new CommentNotExistException(id);
	}

}
