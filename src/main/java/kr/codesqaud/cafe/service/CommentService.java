package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.controller.dto.CommentSaveResponse;
import kr.codesqaud.cafe.controller.dto.req.CommentRequest;
import kr.codesqaud.cafe.domain.comment.Comment;
import kr.codesqaud.cafe.exception.BusinessException;
import kr.codesqaud.cafe.exception.NoAuthorizationException;
import kr.codesqaud.cafe.repository.CommentRepository;

@Transactional(readOnly = true)
@Service
public class CommentService {

	private final CommentRepository commentRepository;

	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Transactional
	public CommentSaveResponse reply(final CommentRequest request, final String userId) {
		Comment comment = commentRepository.save(request.toEntity(userId))
			.orElseThrow(() -> new BusinessException("댓글 등록에 실패했습니다."));
		return CommentSaveResponse.from(comment);
	}

	public void checkDeleteCommentPermission(final Long id, final String userId) {
		commentRepository.findById(id)
			.filter(articleComment -> articleComment.isSameWriter(userId))
			.orElseThrow(NoAuthorizationException::new);
	}

	@Transactional
	public void deleteById(final Long id) {
		commentRepository.deleteById(id);
	}
}
