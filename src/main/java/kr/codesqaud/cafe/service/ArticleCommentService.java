package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.controller.dto.req.ReplyRequest;
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
}
