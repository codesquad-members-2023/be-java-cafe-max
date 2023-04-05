package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.controller.dto.ArticleDto;
import kr.codesqaud.cafe.controller.dto.req.PostingRequest;
import kr.codesqaud.cafe.domain.article.Article;
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
	public void posting(final PostingRequest request) {
		articleRepository.save(Article.from(request));
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
}
