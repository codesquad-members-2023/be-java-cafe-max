package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepository {
    private final List<Article> articleList; //이번에는 List로 해보기

    public ArticleRepository(){
        this.articleList = new ArrayList<>();
    }

    public void newArticle(Article article){
        articleList.add(article);
    }
}
