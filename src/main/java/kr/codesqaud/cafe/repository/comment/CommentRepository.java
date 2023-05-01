package kr.codesqaud.cafe.repository.comment;

import java.util.List;

import kr.codesqaud.cafe.domain.Comment;

public interface CommentRepository {
    List<Comment> findComments(Long commentId);
    Long save(Comment comment);
    void update(Comment comment);
    void deleteCommentId(Long commentId);
    void deletePostId(Long postId);
}
