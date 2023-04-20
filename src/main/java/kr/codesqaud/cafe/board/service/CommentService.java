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

    public void write(CommentWriteForm commentWriteForm, String userId) {
        Comment comment = new Comment.Builder()
                .postId(commentWriteForm.getPostId())
                .writer(userId)
                .contents(commentWriteForm.getContents())
                .build();
        commentJdbcRepository.save(comment);
    }

    public List<CommentResponse> getCommentListByPostId(Long postId) {
        return commentJdbcRepository.findAllByPostId(postId).stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }
}
