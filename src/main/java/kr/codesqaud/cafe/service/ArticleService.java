package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

/**
 * 게시글 관련 서비스 로직을 관리하는 객체
 */
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * 게시글 저장
     * @param article 게시글
     */
    public void save(Article article) {
        // TODO: title/content 비어있는지 등 유효성 검증
        articleRepository.save(article);
    }

    /**
     * 전체 게시글 조회
     * @return 저장소 내 전체 게시글
     */
    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    /**
     * index 로 특정 게시글 조회
     * @param index 게시글 index
     * @return 게시글
     */
    public Optional<Article> findOne(long index) {
        return articleRepository.findByIndex(index);
    }

}
