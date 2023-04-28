package codesquad.cafe.article.repository;

import codesquad.cafe.article.domain.Article;
import codesquad.cafe.global.util.Criteria;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);

    Article findById(Long id);

    String findWriterByUserId(Article article);

    void update(Article article);

    void deletePostById(Long postId);

    List<Article> findPagingArticles(Criteria criteria);

    int getTotal();
}
