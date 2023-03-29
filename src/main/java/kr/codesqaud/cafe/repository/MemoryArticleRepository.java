package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import java.util.*;


@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static Map<Long, Article> storage = new HashMap<>();

    private static long sequence = 0L;


    public void save(Article article){
        article.setId(++sequence);
        storage.put(article.getId(), article);
    }

    public List<Article> findAll(){
        return new ArrayList<>(storage.values());
    }


    public Article findByID(Long index){
        return storage.get(index);
    }

}
