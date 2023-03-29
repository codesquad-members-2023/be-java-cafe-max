package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import java.util.List;


@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static List<Article> storage = new ArrayList<>();


    public void save(Article article){
        storage.add(article);
    }

}
