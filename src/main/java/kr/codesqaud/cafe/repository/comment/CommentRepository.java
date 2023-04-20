package kr.codesqaud.cafe.repository.comment;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Comment;

public interface CommentRepository {

    Comment save(Comment comment);

    int delete(Long id);

    Optional<Comment> findById(Long id);

    List<Comment> findAllByPostId(Long postId);
}
