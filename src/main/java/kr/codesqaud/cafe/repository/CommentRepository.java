package kr.codesqaud.cafe.repository;

import java.util.List;

import kr.codesqaud.cafe.domain.Comment;

public interface CommentRepository {

	void save(Comment comment);

	List<Comment> articleComment(Long id);
}
