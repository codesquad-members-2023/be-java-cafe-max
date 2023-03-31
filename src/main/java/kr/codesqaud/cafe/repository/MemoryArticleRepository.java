package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import java.util.*;


@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static Map<Long, Article> storage = new HashMap<>();

    private static long sequence = 0L;

    //key 값은 글 상세보기 링크에 사용되기에 저장할때 ID 값으로 넣어줌
    public Long save(Article article) {
        article.setId(++sequence);
        storage.put(article.getId(), article);
        return article.getId();
    }

    //글 전체 반환

    public List<Article> findAll() {
        return new ArrayList<>(storage.values());
    }

    //ID로 글 찾아서 반환
    public Article findByID(Long index) {
        return storage.get(index);
    }

}
