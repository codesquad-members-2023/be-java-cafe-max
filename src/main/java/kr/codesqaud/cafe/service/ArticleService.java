package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.controller.dto.ArticleDto;
import kr.codesqaud.cafe.exception.NoAuthorizationException;
import kr.codesqaud.cafe.exception.NotFoundException;
import kr.codesqaud.cafe.repository.ArticleRepository;

@Transactional(readOnly = true)
@Service
public class ArticleService {

	private final ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	@Transactional
	public void posting(final ArticleDto articleDto) {
		articleRepository.save(articleDto.toEntity());
	}

	public List<ArticleDto> getArticles() {
		return articleRepository.findAll()
			.stream()
			.map(ArticleDto::from)
			.collect(Collectors.toUnmodifiableList());
	}

	public ArticleDto findById(final Long id) {
		return articleRepository.findById(id)
			.map(ArticleDto::from)
			.orElseThrow(() -> new NotFoundException(String.format("%d번 게시글을 찾을 수 없습니다.", id)));
	}

	public void validateHasAuthorization(final Long articleId, final String userId) {
		articleRepository.findById(articleId)
			.filter(article -> article.getWriter().equals(userId))
			.orElseThrow(NoAuthorizationException::new);
	}
}
