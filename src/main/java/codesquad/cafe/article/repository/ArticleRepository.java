package codesquad.cafe.article.repository;

import codesquad.cafe.article.domain.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article, final String id);

    List<Article> findAll();

    Article findById(Long id);

    String findWriterByUserId(String writerId);

    void update(Article article);

    void deletePostById(Long postId);
}
