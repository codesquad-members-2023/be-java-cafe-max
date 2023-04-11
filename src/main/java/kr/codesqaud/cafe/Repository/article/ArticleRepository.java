package kr.codesqaud.cafe.Repository.article;

import kr.codesqaud.cafe.domain.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    public void saveArticle(Article article);

    public List<Article> findAllArticle();

    public Optional<Article> findArticleById(int articleId);

}
