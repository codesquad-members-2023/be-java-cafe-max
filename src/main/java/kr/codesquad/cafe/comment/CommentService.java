package kr.codesquad.cafe.comment;

import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment from(String body, Post post, User user) {
        return commentRepository.save(new Comment.Builder()
                .content(body)
                .post(post)
                .user(user)
                .build());
    }

    public void delete(long commentId, long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.delete(userId);
    }
}
