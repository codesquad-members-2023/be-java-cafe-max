package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.ArticleDto;

import java.util.Optional;

public interface ArticleRepository {
    Article saveArticle(ArticleDto articleDto);
    Optional<Article> findByTitle(String title);
    Optional<Article> findByContents(String contents);
}
