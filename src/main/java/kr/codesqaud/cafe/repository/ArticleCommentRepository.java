package kr.codesqaud.cafe.repository;

import java.util.List;

import kr.codesqaud.cafe.domain.articlecomment.ArticleComment;

public interface ArticleCommentRepository {

	List<ArticleComment> findAllByArticleId(Long articleId);
}
