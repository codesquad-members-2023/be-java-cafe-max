package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.comment.Comment;

public interface CommentRepository {

	Optional<Comment> save(Comment comment);

	Optional<Comment> findById(Long id);

	List<Comment> findAllByArticleId(Long articleId);

	void deleteById(Long id);
}
