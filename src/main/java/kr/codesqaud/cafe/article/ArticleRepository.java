package kr.codesqaud.cafe.article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    String findIdBySequence(long sequence);

    Optional<Article> findBySequence(long sequence);

    List<Article> findAll();

    Article update(long sequence, Article article);
}
