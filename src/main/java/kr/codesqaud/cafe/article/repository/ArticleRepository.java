package kr.codesqaud.cafe.article.repository;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticleFormDto;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ArticleRepository {

    Long save(Article article);

    List<Article> findAll();

    Article findByID(Long index);

    Long modify(long id, ArticleFormDto articleFormDto);
}
