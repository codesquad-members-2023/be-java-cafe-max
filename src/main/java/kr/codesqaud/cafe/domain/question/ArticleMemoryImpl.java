package kr.codesqaud.cafe.domain.question;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleMemoryImpl implements ArticleRepository{
    public static List<Article> articleList = new ArrayList<>();
    @Override
    public void save(Article article) {
        articleList.add(article);
    }

    @Override
    public List<Article> findAll() {
        return articleList;
    }
}
