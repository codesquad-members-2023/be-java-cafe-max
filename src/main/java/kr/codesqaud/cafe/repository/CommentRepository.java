package kr.codesqaud.cafe.repository;

import java.util.List;

import kr.codesqaud.cafe.domain.Comment;

public interface CommentRepository {

	void save(Comment comment);

	List<Comment> findAllCommentsByArticleId(Long id);

	Long commentCountingByArticleId(Long articleId);

	void delete(Long id);
}
