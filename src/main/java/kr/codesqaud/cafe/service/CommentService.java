package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.controller.dto.CommentDto;
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

	public List<CommentDto> articleComment(Long id) {
		return commentRepository.articleComment(id)
			.stream()
			.map(CommentDto::fromEntity)
			.collect(Collectors.toUnmodifiableList());
	}
}
