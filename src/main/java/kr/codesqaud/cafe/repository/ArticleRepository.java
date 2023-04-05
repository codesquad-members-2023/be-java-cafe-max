package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {
    List<Article> repository = new ArrayList<>();

    public void save(Article article) {
        repository.add(article);
    }

    public List<Article> getAllArticle() {
        return repository;
    }
}
