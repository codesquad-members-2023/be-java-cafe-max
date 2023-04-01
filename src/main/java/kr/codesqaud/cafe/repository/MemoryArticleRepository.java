package kr.codesqaud.cafe.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public Article save(final Article article) {
        article.setIndex(++index);
        ARTICLE_REPOSITORY.put(article.getIndex(), article);
        return article;
    }

    @Override
    public Optional<Article> findByIndex(final long index) {
        return Optional.ofNullable(ARTICLE_REPOSITORY.get(index));
    }

    @Override
    public List<Article> findAll() {
        return ARTICLE_REPOSITORY.values().stream()
                .collect(Collectors.toUnmodifiableList());
    }

    public void clear() {
        ARTICLE_REPOSITORY.clear();
    }
}
