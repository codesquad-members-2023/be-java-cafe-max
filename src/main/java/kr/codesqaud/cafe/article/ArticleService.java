package kr.codesqaud.cafe.article;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticlePostRequest;
import kr.codesqaud.cafe.article.dto.ArticleResponse;
import kr.codesqaud.cafe.article.dto.ArticleTitleAndContentResponse;
import kr.codesqaud.cafe.article.dto.ArticleUpdateRequest;
import kr.codesqaud.cafe.article.exception.ArticleDeleteException;
import kr.codesqaud.cafe.article.exception.ArticleIdAndSessionIdMismatchException;
import kr.codesqaud.cafe.article.exception.ArticleNotFoundException;
import kr.codesqaud.cafe.article.repository.ArticleRepository;
import kr.codesqaud.cafe.global.mapper.ArticleMapper;

@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
	private final ArticleMapper articleMapper;

	public ArticleService(ArticleRepository articleRepository,
		ArticleMapper articleMapper) {
		this.articleRepository = articleRepository;
		this.articleMapper = articleMapper;
	}

	public void post(ArticlePostRequest articlePostRequest) {
		Article article = articleMapper.toArticle(articlePostRequest);
		articleRepository.save(article);
	}

	public List<ArticleResponse> getArticleList() {
		return articleRepository.findAll().stream()
			.sorted(Comparator.comparing(Article::getArticleIdx).reversed())
			.map(articleMapper::toArticleResponse)
			.collect(Collectors.toUnmodifiableList());
	}

	public ArticleResponse findArticleByIdx(Long articleIdx) {
		return articleRepository.findArticleByIdx(articleIdx)
			.map(articleMapper::toArticleResponse)
			.orElseThrow(ArticleNotFoundException::new);
	}

	public void updateArticle(ArticleUpdateRequest articleUpdateRequest) {
		articleRepository.updateArticle(articleMapper.toArticle(articleUpdateRequest));
	}

	public ArticleTitleAndContentResponse validSessionIdAndArticleId(Long articleIdx, String userId) {
		return articleRepository.findArticleByIdx(articleIdx)
			.filter(article -> userId.equals(article.getUserId()))
			.map(articleMapper::toArticleTitleAndContentResponse)
			.orElseThrow(ArticleIdAndSessionIdMismatchException::new);
	}

	public void deleteArticleByIdx(Long articleIdx, String userId) {
		validSessionIdAndArticleId(articleIdx, userId);
		if (!articleRepository.deleteArticle(articleIdx, userId)) {
			throw new ArticleDeleteException();
		}
	}
}
