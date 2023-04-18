package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.controller.dto.req.ReplyRequest;
import kr.codesqaud.cafe.exception.NoAuthorizationException;
import kr.codesqaud.cafe.repository.ArticleCommentRepository;

@Transactional(readOnly = true)
@Service
public class ArticleCommentService {

	private final ArticleCommentRepository articleCommentRepository;

	public ArticleCommentService(ArticleCommentRepository articleCommentRepository) {
		this.articleCommentRepository = articleCommentRepository;
	}

	@Transactional
	public void reply(final ReplyRequest request, final String userId) {
		articleCommentRepository.save(request.toEntity(userId));
	}

	public void validateHasAuthorization(final Long id, final String userId) {
		articleCommentRepository.findById(id)
			.filter(articleComment -> articleComment.isSameWriter(userId))
			.orElseThrow(NoAuthorizationException::new);
	}

	@Transactional
	public void deleteById(final Long id) {
		articleCommentRepository.deleteById(id);
	}
}
