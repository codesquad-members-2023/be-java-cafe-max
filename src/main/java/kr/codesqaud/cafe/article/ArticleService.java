package kr.codesqaud.cafe.article;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticlePostRequest;
import kr.codesqaud.cafe.article.dto.ArticleResponse;
import kr.codesqaud.cafe.article.dto.ArticleTitleAndContentResponse;
import kr.codesqaud.cafe.article.dto.ArticleUpdateRequest;
import kr.codesqaud.cafe.article.dto.ReplyRequest;
import kr.codesqaud.cafe.article.dto.ReplyResponse;
import kr.codesqaud.cafe.article.exception.ArticleIdAndSessionIdMismatchException;
import kr.codesqaud.cafe.article.exception.ArticleNotFoundException;
import kr.codesqaud.cafe.article.repository.ArticleRepository;
import kr.codesqaud.cafe.global.mapper.ArticleMapper;

@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
	private final ArticleMapper articleMapper;

	public ArticleService(@Qualifier("jdbcRepository") ArticleRepository articleRepository,
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
			.sorted(Comparator.comparing(Article::getIdx).reversed())
			.map(articleMapper::toArticleResponse)
			.collect(Collectors.toUnmodifiableList());
	}

	public ArticleResponse findArticleByIdx(Long idx) {
		return articleRepository.findArticleByIdx(idx)
			.map(articleMapper::toArticleResponse)
			.orElseThrow(ArticleNotFoundException::new);
	}

	public void updateArticle(ArticleUpdateRequest articleUpdateRequest) {
		articleRepository.updateArticle(articleMapper.toArticle(articleUpdateRequest));
	}

	public ArticleTitleAndContentResponse validSessionIdAndArticleId(Long idx, String id) {
		return articleRepository.findArticleByIdx(idx)
			.filter(article -> id.equals(article.getId()))
			.map(articleMapper::toArticleTitleAndContentResponse)
			.orElseThrow(ArticleIdAndSessionIdMismatchException::new);
	}

	public void deleteArticleByIdx(Long idx, String id) {
		validSessionIdAndArticleId(idx, id);
		articleRepository.deleteArticle(idx);
	}

	public void addReply(ReplyRequest replyRequest) {
		articleRepository.saveReply(articleMapper.toReply(replyRequest));
	}

	public List<ReplyResponse> getReplyListByIdx(Long idx) {
		return articleRepository.findAllReply(idx).stream()
			.map(articleMapper::toReplyResponse)
			.collect(Collectors.toUnmodifiableList());
	}

	public void deleteReply(String id, Long replyIdx) {
		articleRepository.deleteReply(id, replyIdx);
	}
}
