package kr.codesqaud.cafe.repository.comment;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Comment;

public interface CommentRepository {
	List<Comment> findByArticleIndex(Long articleIndex);

	void create(Comment comment);

	Optional<Comment> findOne(Long articleIndex, Long commentIndex);

	void delete(Long articleIndex, Long commentIndex);

	void deleteAll(Long articleIndex);
}
