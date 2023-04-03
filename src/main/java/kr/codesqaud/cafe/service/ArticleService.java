package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.controller.dto.ArticleDto;
import kr.codesqaud.cafe.controller.dto.PostingRequest;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;

@Service
public class ArticleService {

	private final MemoryArticleRepository articleRepository;

	public ArticleService(MemoryArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public void articleSave(PostingRequest postingRequest) {
		Article article = postingRequest.getArticleEntity();
		articleRepository.save(article);
	}

	public List<ArticleDto> allListLookup() {
		return articleRepository.findAllPosting()
			.stream()
			.map(ArticleDto::fromEntity)
			.collect(Collectors.toUnmodifiableList());
	}

	public ArticleDto findById(Long id) {
		return ArticleDto.fromEntity(articleRepository.findPosting(id));
	}
}
