package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.controller.dto.req.ReplyRequest;
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
	public Long reply(final ReplyRequest request, final String userId) {
		return commentRepository.save(request.toEntity(userId));
	}

	public void validateHasAuthorization(final Long id, final String userId) {
		commentRepository.findById(id)
			.filter(articleComment -> articleComment.isSameWriter(userId))
			.orElseThrow(NoAuthorizationException::new);
	}

	@Transactional
	public void deleteById(final Long id) {
		commentRepository.deleteById(id);
	}
}
