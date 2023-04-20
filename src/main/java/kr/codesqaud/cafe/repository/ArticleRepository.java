package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {
    private final List<Article> articleList; //이번에는 List로 해보기
    private static long articleNumFactory = 0;  //Factory를 여기로 //AtomicLong 쓸 수도 있었을 듯

    public ArticleRepository() {
        this.articleList = new ArrayList<>();
    }

    public void save(Article article) {
        ++articleNumFactory; // 전위연산자...!
        article.setArticleNum(articleNumFactory);
        String cutCreatedTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE); // 밑엣 줄에 '함수를 매개변수로 사용'하려고 했는데 안 됨(이유 모름)
        article.setCreatedTime(cutCreatedTime);
        articleList.add(article);
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public Optional<Article> getArticleByArticleNum(long articleNum) {
        return articleList.stream().filter(article -> article.getArticleNum() == articleNum).findAny();  // .Nullable은 없는데 Optional을 만드는 듯(?)
    }
}
