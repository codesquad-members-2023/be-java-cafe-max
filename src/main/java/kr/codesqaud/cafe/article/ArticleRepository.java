package kr.codesqaud.cafe.article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    long save(Article article);

    String findIdBySequence(long sequence);

    Optional<Article> findOneById(long id);

    List<Article> findAll();

    long update(long id, Article article);
}
