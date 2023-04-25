package kr.codesqaud.cafe.board.service;

import kr.codesqaud.cafe.board.domain.Comment;
import kr.codesqaud.cafe.board.dto.CommentResponse;
import kr.codesqaud.cafe.board.dto.CommentWriteForm;
import kr.codesqaud.cafe.board.repository.CommentJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentJdbcRepository commentJdbcRepository;

    public CommentService(CommentJdbcRepository commentJdbcRepository) {
        this.commentJdbcRepository = commentJdbcRepository;
    }

    public Long write(CommentWriteForm commentWriteForm, String userName) {
        Comment comment = Comment.builder()
                .postId(commentWriteForm.getPostId())
                .writer(userName)
                .contents(commentWriteForm.getContents())
                .build();
        return commentJdbcRepository.save(comment);
    }

    public CommentResponse getComment(Long commentId) {
        return CommentResponse.from(commentJdbcRepository.findByCommentId(commentId));
    }

    public List<CommentResponse> getCommentListByPostId(Long postId) {
        return commentJdbcRepository.findAllByPostId(postId).stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }

    public void delete(Long commentId) {
        commentJdbcRepository.delete(commentId);
    }

    public boolean isSameWriter(Long commentId, String sessionUserName) {
        Comment comment = commentJdbcRepository.findByCommentId(commentId);
        return sessionUserName.equals(comment.getWriter());
    }
}
