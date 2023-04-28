package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.comment.CommentDTO;
import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.repository.CommentRepository;
import kr.codesqaud.cafe.util.LoginSessionManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final LoginSessionManager loginSessionManager;

    public CommentService(CommentRepository commentRepository, LoginSessionManager loginSessionManager) {
        this.commentRepository = commentRepository;
        this.loginSessionManager = loginSessionManager;
    }

    public void write(Long articleId, CommentDTO commentDto) {
        Long userId = loginSessionManager.getLoginUser().getId();
        String userName = loginSessionManager.getLoginUser().getName();
        Comment comment = commentDto.toEntity(articleId, userId, userName);
        commentRepository.save(comment);
    }

    public List<CommentDTO> gather(Long articleId) {
        return commentRepository.gatherAllByArticleId(articleId).stream()
                .map(CommentDTO::from)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
