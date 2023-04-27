package kr.codesqaud.cafe.app.comment.repository;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.app.comment.entity.Comment;

public interface CommentRepository {

    List<Comment> findAll(Long questionId);

    Optional<Comment> findById(Long id);

    Comment save(Comment comment);

    Comment modify(Comment comment);

    Comment deleteById(Long id);

    void deleteAllByQuestionId(Long questionId);
}
