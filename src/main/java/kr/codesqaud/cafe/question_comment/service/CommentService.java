package kr.codesqaud.cafe.question_comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.question_comment.domain.CommentEntity;
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

}
