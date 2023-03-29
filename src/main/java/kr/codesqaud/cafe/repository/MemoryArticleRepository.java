package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

/**
 * 작성된 게시글을 저장하는 저장소
 */
@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static final Map<Long, Article> ARTICLE_REPOSITORY = new HashMap<>();
    private static long index = 0L;

    @Override
    public Article save(Article article) {
        article.setIndex(++index);
        ARTICLE_REPOSITORY.put(article.getIndex(), article);
        return article;
    }

    @Override
    public Optional<Article> findByIndex(long index) {
        return Optional.ofNullable(ARTICLE_REPOSITORY.get(index));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(ARTICLE_REPOSITORY.values());
    }

    public void clear() {
        ARTICLE_REPOSITORY.clear();
    }
}
