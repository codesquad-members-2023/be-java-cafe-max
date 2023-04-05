package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleDto;

public interface ArticleRepository {
	Article saveArticle(ArticleDto articleDto);

	Optional<Article> findByIndex(Long index);

	Optional<Article> findByTitle(String title);

	Optional<Article> findByContents(String contents);

	List<Article> findAll();
}
