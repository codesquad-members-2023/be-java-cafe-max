package kr.codesqaud.cafe.repository.comment;

import java.util.List;

import kr.codesqaud.cafe.domain.Comment;

public interface CommentRepository {
	List<Comment> findByPostIndex(long index);

	void create(Comment comment);

	Comment findOne(Long postIndex, Long index);

	void delete(Long postIndex, Long index);

	void deleteAll(Long postIndex);
}
