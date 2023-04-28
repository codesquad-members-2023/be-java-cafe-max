package kr.codesqaud.cafe.repository.comment;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Comment;

public interface CommentRepository {
	void create(Comment comment);

	Optional<Comment> findOne(Long articleIndex, Long commentIndex);

	void delete(Long articleIndex, Long commentIndex);

	void deleteAll(Long articleIndex);

	Integer getCommentsSize(Long articleIndex);

	Boolean equalsAuthor(Long articleIndex);

	List<Comment> findComments(Long articleIndex, Long commentLastIndex);
}
