package kr.codesqaud.cafe.article.repository;

import kr.codesqaud.cafe.article.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ArticleRepository {

    Long save(Article article);

    List<Article> findAll();

    public Article findByID(Long index);
}
