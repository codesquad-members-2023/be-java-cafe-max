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
    private final List<Article> articleList; //이번에는 List로 해보기 -> 큰 이유가 없어서 언제든 변경 가능
    private static long articleNumFactory = 0;  //Factory를 여기로 -> (Fatory가 적당한가?! 확인 후 수정 예정) //AtomicLong 쓸 수도 있었을 듯

    public BasicArticleRepository() {
        this.articleList = new ArrayList<>();
    }

    @Override
    public void save(Article article) {
        ++articleNumFactory; // 전위연산자...!
        article.setArticleNum(articleNumFactory);
        String cutCreatedTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE); // 밑엣 줄에 '함수를 매개변수로 사용'하려고 했는데 안 됨(이유 모름)
        article.setCreatedTime(LocalDateTime.parse(cutCreatedTime));
        articleList.add(article);
    }
    @Override
    public List<Article> getArticleList() {
        return articleList;
    }
    @Override
    public Optional<Article> getArticleByArticleNum(Long articleNum) {
        return articleList.stream().filter(article -> article.getArticleNum() == articleNum).findAny();  // .Nullable은 없는데 Optional을 만드는 듯(?)
    }

    public void clearStore(){};
}
