package kr.codesqaud.cafe.comment.service;

import kr.codesqaud.cafe.comment.domain.Comment;
import kr.codesqaud.cafe.comment.dto.RequestForm;
import kr.codesqaud.cafe.comment.mapper.CommentDtoMapper;
import kr.codesqaud.cafe.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void save(RequestForm requestForm, String userId) {
        commentRepository.save(CommentDtoMapper.INSTANCE.toComment(requestForm, userId));
    }

    public List<Comment> getCommentsForArticle(long id){
        return commentRepository.findByArticleId(id);
    }

    public void delete(long commentId) {
        commentRepository.deleteById(commentId);
    }
}
