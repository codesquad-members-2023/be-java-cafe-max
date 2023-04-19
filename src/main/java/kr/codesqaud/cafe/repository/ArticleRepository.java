package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepository {
    private final List<Article> articleList; //이번에는 List로 해보기
    private static long articleNumFactory = 0;  //Factory는 여기에...

    public ArticleRepository(){
        this.articleList = new ArrayList<>();
    }

    public void save(Article article){
        articleNumFactory += 1 ;
        article.setArticleNum(articleNumFactory);
        articleList.add(article);
    }
    public List<Article> getArticleList(){
        return articleList;
    }
}
