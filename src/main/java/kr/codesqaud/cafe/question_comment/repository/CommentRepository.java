package kr.codesqaud.cafe.question_comment.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.question_comment.domain.CommentEntity;

public interface CommentRepository {
	void save(CommentEntity comment);

	List<CommentEntity> findByPostId(long post_id);

	public Optional<CommentEntity> findById(long id);

	public boolean deleteById(long id);
}
