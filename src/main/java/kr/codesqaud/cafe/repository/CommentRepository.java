package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Comment;

import java.util.List;

public interface CommentRepository {
    void save(final Comment comment);

    List<Comment> gatherAllByArticleId(final Long articleId);
}
