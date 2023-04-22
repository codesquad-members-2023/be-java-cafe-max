package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.articlecomment.Comment;

public interface CommentRepository {

	Long save(Comment comment);

	Optional<Comment> findById(Long id);

	List<Comment> findAllByArticleId(Long articleId);

	void deleteById(Long id);
}
