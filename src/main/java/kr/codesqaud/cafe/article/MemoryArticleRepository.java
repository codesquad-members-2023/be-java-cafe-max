package kr.codesqaud.cafe.article;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 메모리 게시글 저장소 구현체
 */
@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private List<Article> articles;

    public MemoryArticleRepository(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public void save(Article article) {
        articles.add(article);
    }

    @Override
    public List<Article> findAll() {
        return articles;
    }

    @Override
    public Article findById(int articleId) {
        for (Article article : articles) {
            if (article.getArticleId() == articleId) {
                return article;
            }
        }
        return null;
    }
}
