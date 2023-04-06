package kr.codesqaud.cafe.article;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시글 저장소 인터페이스
 */
@Repository
public interface ArticleRepository {
    void save(Article article);

    List<Article> findAll();

    Article findById(int articleId);
}
