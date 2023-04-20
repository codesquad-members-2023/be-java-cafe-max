package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.dto.comment.CommentResponse;
import kr.codesqaud.cafe.repository.comment.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId)
            .stream()
            .map(CommentResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }
}
