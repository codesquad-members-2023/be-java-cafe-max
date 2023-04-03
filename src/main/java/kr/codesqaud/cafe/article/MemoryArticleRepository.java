package kr.codesqaud.cafe.article;


import java.util.List;

/**
 * 메모리 게시글 저장소 구현체
 */
public class MemoryArticleRepository implements ArticleRepository {
    private List<Article> articles;

    public MemoryArticleRepository(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public void save(Article article) {
        articles.add(article);
    }
}
