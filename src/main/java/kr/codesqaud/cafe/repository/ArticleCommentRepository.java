package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.articlecomment.ArticleComment;

public interface ArticleCommentRepository {

	Optional<ArticleComment> save(ArticleComment articleComment);
	
	List<ArticleComment> findAllByArticleId(Long articleId);
}
