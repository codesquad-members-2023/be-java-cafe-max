package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.articlecomment.ArticleComment;

public interface ArticleCommentRepository {

	Optional<ArticleComment> save(ArticleComment articleComment);

	Optional<ArticleComment> findById(Long id);

	List<ArticleComment> findAllByArticleId(Long articleId);

	void deleteById(Long id);
}
