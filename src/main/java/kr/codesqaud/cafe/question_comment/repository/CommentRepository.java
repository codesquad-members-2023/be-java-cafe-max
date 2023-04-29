package kr.codesqaud.cafe.question_comment.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.question_comment.domain.CommentEntity;

public interface CommentRepository {
	long save(CommentEntity comment);

	List<CommentEntity> findByPostIdAndSize(long post_id, int size);

	public List<CommentEntity> findByPostIdAndCursorAndSize(long post_id, long cursor, int size);

	public Optional<CommentEntity> findById(long id);

	public boolean deleteById(long id);
}
