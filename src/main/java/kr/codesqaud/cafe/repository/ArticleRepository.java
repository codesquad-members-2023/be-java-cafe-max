package kr.codesqaud.cafe.repository;

import java.util.List;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleDto;

public interface ArticleRepository {
	Article create(Article article);

	Article findByIndex(Long index);

	List<Article> findAll();

	boolean increaseHits(Long index);

	boolean delete(Long index);

	boolean update(Long index, ArticleDto articleDto);

	boolean updateWriter(String originalNickname, String newNickname);
}
