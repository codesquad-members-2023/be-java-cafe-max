package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryArticleRepository implements ArticleRepository{
    List<Article> repository = new ArrayList<>();

    @Override
    public void save(Article article) {
        repository.add(0, article);
    }

    @Override
    public List<Article> findAll() {
        return repository;
    }

    @Override
    public Article findById(int index) {
        return repository.get(index);
    }
}
