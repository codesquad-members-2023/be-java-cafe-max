package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.controller.dto.ArticleDto;
import kr.codesqaud.cafe.controller.dto.PostingRequest;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.exception.InvalidSessionException;
import kr.codesqaud.cafe.exception.NoAuthorizationException;
import kr.codesqaud.cafe.repository.ArticleRepository;

@Service
public class ArticleService {

	private final ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	@Transactional
	public void articleSave(PostingRequest postingRequest, String userId) {
		if (userId == null) {
			throw new InvalidSessionException();
		}
		Article article = postingRequest.getArticleEntity(userId);
		articleRepository.save(article);
	}

	@Transactional
	public List<ArticleDto> allListLookup() {
		return articleRepository.findAllPosting()
			.stream()
			.map(ArticleDto::fromEntity)
			.collect(Collectors.toUnmodifiableList());
	}

	@Transactional
	public ArticleDto findById(Long id) {
		return ArticleDto.fromEntity(articleRepository.findPosting(id));
	}

	@Transactional
	public void deleteRequest(Long id) {
		articleRepository.delete(id);
	}

	@Transactional
	public void updateRequest(PostingRequest postingRequest, Long id, String writer) {
		Article article = postingRequest.getArticleEntity(writer);
		articleRepository.update(article, id);
	}

	@Transactional(readOnly = true)
	public void validateAuthorization(Long articleId, String userId) {
		Article article = articleRepository.findPosting(articleId);
		if (!article.getWriter().equals(userId)) {
			throw new NoAuthorizationException();
		}
	}
}
