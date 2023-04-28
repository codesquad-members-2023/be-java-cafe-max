package kr.codesqaud.cafe.comment.service;

import kr.codesqaud.cafe.comment.domain.Comment;
import kr.codesqaud.cafe.comment.dto.RequestCommentForm;
import kr.codesqaud.cafe.comment.mapper.CommentDtoMapper;
import kr.codesqaud.cafe.comment.repository.CommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment save(RequestCommentForm requestCommentForm, String userId) {
        long savedId = commentRepository.save(CommentDtoMapper.INSTANCE.toComment(requestCommentForm, userId));
        return commentRepository.findById(savedId);
    }

    public List<Comment> getCommentsForArticle(long id){
        return commentRepository.findByArticleId(id);
    }

    public HttpStatus delete(long commentId, String userId, String sessionId) {
        try {
            if (sessionId.equals(userId)) {
                commentRepository.deleteById(commentId);
                return HttpStatus.OK;
            } else {
                return HttpStatus.FORBIDDEN;
            }
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
