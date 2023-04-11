package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {
    public void save(Article article);

    public List<Article> getAllArticle();

    public Article getArticleIndexOf(int index);
}
