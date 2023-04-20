package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {
    private final List<Article> articleList; //이번에는 List로 해보기
    private static long articleNumFactory = 0;  //Factory를 여기로 //AtomicLong 쓸 수도 있었을 듯
    public ArticleRepository(){
        this.articleList = new ArrayList<>();
    }
    public void save(Article article){
        ++articleNumFactory;
        article.setArticleNum(articleNumFactory);
        articleList.add(article);
    }
    public List<Article> getArticleList(){
        return articleList;
    }

    public Optional<Article> getArticleByArticleNum(Long articleNum){
        return articleList.stream().filter(article -> article.getArticleNum() == articleNum).findAny();
    }
}
