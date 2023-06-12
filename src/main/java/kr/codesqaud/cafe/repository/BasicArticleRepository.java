package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BasicArticleRepository implements ArticleRepository {
    private final List<Article> articleList;
    private static long articleIdFactory = 0;

    public BasicArticleRepository() {
        this.articleList = new ArrayList<>();
    }
    @Override
    public void save(Article article) {
        ++articleIdFactory;
        article.setId(articleIdFactory);
        String cutCreatedTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        article.setCreatedTime(LocalDateTime.parse(cutCreatedTime));
        articleList.add(article);
    }
    @Override
    public List<Article> getArticleList() {
        return articleList;
    }
    @Override
    public Optional<Article> getArticleById(Long id) {
        return articleList.stream().filter(article -> article.getId() == id).findAny();
    }

    public void clearStore(){}
}
