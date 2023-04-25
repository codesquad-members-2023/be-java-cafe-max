package kr.codesqaud.cafe.app.comment.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.app.comment.controller.dto.CommentModifyRequest;
import kr.codesqaud.cafe.app.comment.controller.dto.CommentResponse;
import kr.codesqaud.cafe.app.comment.controller.dto.CommentSavedRequest;
import kr.codesqaud.cafe.app.comment.entity.Comment;
import kr.codesqaud.cafe.app.comment.repository.CommentRepository;
import kr.codesqaud.cafe.errors.errorcode.CommentErrorCode;
import kr.codesqaud.cafe.errors.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public CommentResponse answerComment(CommentSavedRequest commentSavedRequest) {
        Comment savedComment = commentRepository.save(commentSavedRequest.toEntity());
        return new CommentResponse(savedComment);
    }

    public List<CommentResponse> getComments(Long questionId) {
        List<Comment> comments = commentRepository.findAll(questionId);

        return comments.stream()
            .map(CommentResponse::new)
            .sorted()
            .collect(Collectors.toUnmodifiableList());
    }

    public CommentResponse getComment(Long id) {
        Comment findComment = commentRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(CommentErrorCode.NOT_FOUND_COMMENT);
        });

        return new CommentResponse(findComment);
    }

    @Transactional
    public CommentResponse modifyComment(CommentModifyRequest commentRequest) {
        Comment modifyComment = commentRepository.modify(commentRequest.toEntity());
        return new CommentResponse(modifyComment);
    }

    @Transactional
    public CommentResponse deleteComment(Long id) {
        Comment delComment = commentRepository.deleteById(id);
        return new CommentResponse(delComment);
    }
}
