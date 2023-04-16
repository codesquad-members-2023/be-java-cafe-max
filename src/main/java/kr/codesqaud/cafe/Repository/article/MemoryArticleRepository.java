//package kr.codesqaud.cafe.Repository.article;
//
//import kr.codesqaud.cafe.Repository.article.ArticleRepository;
//import kr.codesqaud.cafe.domain.Article;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//import java.util.concurrent.atomic.AtomicLong;
//
//@Repository
//public class MemoryArticleRepository implements ArticleRepository {
//    private final List<Article> articleRepository = new ArrayList<>();
//    //    private final Map<Integer, Article> articleRepository = new HashMap<>();
//    private AtomicLong sequence = new AtomicLong();
//
//    public void saveArticle(final Article article) {
//        articleRepository.add(article);
//        article.setId(sequence.getAndIncrement());
//    }
//
//    public List<Article> findAllArticle() {
//        return Collections.unmodifiableList(new ArrayList<>(articleRepository));
//    }
//
//    public Optional<Article> findArticleById(final int articleId) {
//        return Optional.ofNullable(articleRepository.get(articleId ));
//    }
//
//
//}
