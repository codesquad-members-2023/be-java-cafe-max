package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    public void saveArticle(Article article);

    public List<Article> findAllArticle();

    public Optional<Article> findArticleById(int articleId);

}
