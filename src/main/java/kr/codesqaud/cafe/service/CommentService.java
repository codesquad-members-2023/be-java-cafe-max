package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.dto.comment.CommentDeleteResponse;
import kr.codesqaud.cafe.dto.comment.CommentPagination;
import kr.codesqaud.cafe.dto.comment.CommentResponse;
import kr.codesqaud.cafe.dto.comment.CommentWriteRequest;
import kr.codesqaud.cafe.dto.comment.CommentsResponse;
import kr.codesqaud.cafe.exception.comment.ApiUnauthorizedException;
import kr.codesqaud.cafe.repository.comment.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public CommentResponse write(CommentWriteRequest commentWriteRequest) {
        Comment comment = commentWriteRequest.toComment(commentWriteRequest.getWriter()
            .toMember());
        return CommentResponse.from(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public CommentsResponse findAllByPostId(Long postId, Integer page) {
        CommentPagination commentPagination = new CommentPagination(page,
            commentRepository.existNextPaginationByPage(postId, page, CommentPagination.MAX_COMMENT_SIZE));
        List<CommentResponse> commentResponses = commentRepository.findAllByPostId(postId,
                commentPagination.getOffset(), CommentPagination.MAX_COMMENT_SIZE)
            .stream()
            .map(CommentResponse::from)
            .collect(Collectors.toUnmodifiableList());
        return new CommentsResponse(commentResponses, commentPagination.isNext());
    }

    public CommentDeleteResponse delete(Long id, Long accountSessionId) {
        validateUnauthorized(id, accountSessionId);
        return new CommentDeleteResponse(commentRepository.delete(id) != 0);
    }

    private void validateUnauthorized(Long id, Long accountSessionId) {
        if (!commentRepository.existByIdAndMemberId(id, accountSessionId)) {
            throw new ApiUnauthorizedException();
        }
    }
}
