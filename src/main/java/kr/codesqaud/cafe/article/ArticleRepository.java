package kr.codesqaud.cafe.article;

/**
 * 게시글 저장소 인터페이스
 */
public interface ArticleRepository {
    void save(Article article);
}
