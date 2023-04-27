package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    void save(final Comment comment);

    void deleteById(final Long id);

    void deleteByReId(final Long reId);

    Optional<Comment> findById(final Long id);

    List<Comment> gatherAll();
}
