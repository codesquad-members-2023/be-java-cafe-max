package kr.codesqaud.cafe.board.service;

import kr.codesqaud.cafe.board.domain.Comment;
import kr.codesqaud.cafe.board.dto.CommentResponse;
import kr.codesqaud.cafe.board.dto.CommentWriteForm;
import kr.codesqaud.cafe.board.repository.CommentJdbcRepository;
import kr.codesqaud.cafe.exception.ForbiddenException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentJdbcRepository commentJdbcRepository;

    public CommentService(CommentJdbcRepository commentJdbcRepository) {
        this.commentJdbcRepository = commentJdbcRepository;
    }

    public void write(CommentWriteForm commentWriteForm, String userName) {
        Comment comment = new Comment.Builder()
                .postId(commentWriteForm.getPostId())
                .writer(userName)
                .contents(commentWriteForm.getContents())
                .build();
        commentJdbcRepository.save(comment);
    }

    public CommentResponse getComment(Long commentId) {
        return CommentResponse.from(commentJdbcRepository.findByCommentId(commentId));
    }

    public List<CommentResponse> getCommentListByPostId(Long postId) {
        return commentJdbcRepository.findAllByPostId(postId).stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }

    public void delete(Long commentId, String sessionUserName) {
        Comment comment = commentJdbcRepository.findByCommentId(commentId);
        if (sessionUserName.equals(comment.getWriter())) {
            commentJdbcRepository.delete(commentId);
        } else {
            throw new ForbiddenException("댓글 작성자가 아니어서 삭제할 수 없습니다.");
        }
    }
}
