package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {
    List<Article> repository = new ArrayList<>();

    public void save(Article article) {
        repository.add(0, article);
    }

    public List<Article> getAllArticle() {
        return repository;
    }

    public Article getArticleIndexOf(int index) {
        return repository.get(index);
    }
}
