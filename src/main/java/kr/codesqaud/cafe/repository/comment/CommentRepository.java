package kr.codesqaud.cafe.repository.comment;

import java.util.List;
import kr.codesqaud.cafe.domain.Comment;

public interface CommentRepository {

    Comment save(Comment comment);

    void delete(Long id);

    List<Comment> findAllByPostId(Long postId);
}
