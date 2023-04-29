package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.dto.comment.CommentListDto;
import kr.codesqaud.cafe.dto.comment.CommentUpdateDto;
import kr.codesqaud.cafe.dto.comment.CommentWriteDto;
import kr.codesqaud.cafe.exception.common.CommonException;
import kr.codesqaud.cafe.repository.comment.CommentRepository;

import static kr.codesqaud.cafe.exception.common.CommonExceptionType.ACCESS_DENIED;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Long save(CommentWriteDto commentWriteDto) {
        return commentRepository.save(commentWriteDto.toEntity());
    }

    @Transactional(readOnly = true)
    public CommentListDto findComments(Long postId) {
        return CommentListDto.of(commentRepository.findComments(postId));
    }


    @Transactional
    public void update(CommentUpdateDto commentUpdateDto) {
        commentRepository.update(commentUpdateDto.toEntity());
    }

    @Transactional
    public void deleteId(String commentMemberEmail, String writerEmail, Long commentId) {
        validateCommentUser(writerEmail, commentMemberEmail);
        commentRepository.deleteId(commentId);
    }

    private void validateCommentUser(final String writerEmail, final String commentMemberEmail) {
        if (!writerEmail.equals(commentMemberEmail)) {
            throw new CommonException(ACCESS_DENIED);
        }
    }
}
