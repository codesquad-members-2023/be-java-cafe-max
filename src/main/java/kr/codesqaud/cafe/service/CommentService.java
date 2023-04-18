package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.controller.dto.CommentRequest;
import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.repository.CommentRepository;

@Service
@Transactional
public class CommentService {

	private final CommentRepository commentRepository;

	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	public void CommentSave(CommentRequest request, String userId, Long articleId) {
		Comment comment = new Comment(userId, request.getContents(), articleId);
		commentRepository.save(comment);
	}

}
