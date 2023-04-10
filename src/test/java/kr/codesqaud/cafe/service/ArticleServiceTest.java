package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.dto.article.ArticleResponse;
import kr.codesqaud.cafe.dto.article.ArticleSaveRequest;
import kr.codesqaud.cafe.exception.article.ArticleNotFoundException;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class ArticleServiceTest {
    ArticleService articleService;

    @Qualifier("jdbcArticleRepository")
    @Autowired
    ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        this.articleService = new ArticleService(articleRepository);
    }

    @DisplayName("articleSaveRequest가 DB에 정상적으로 저장됐는지 확인하는 테스트")
    @Test
    void saveArticle() {
        // given
        ArticleSaveRequest articleSaveRequest = new ArticleSaveRequest("title1", "writer1", "contents1");

        // when
        Long id = articleService.saveArticle(articleSaveRequest);

        // then
        ArticleResponse articleResponse = articleService.findById(id);
        assertThat(articleResponse.getId()).isEqualTo(id);
        assertThat(articleResponse.getTitle()).isEqualTo(articleSaveRequest.getTitle());
        assertThat(articleResponse.getWriter()).isEqualTo(articleSaveRequest.getWriter());
        assertThat(articleResponse.getContents()).isEqualTo(articleSaveRequest.getContents());
    }

    @DisplayName("getAllArticles를 통해 모든 article을 List로 가져오는지 확인하는 테스트")
    @Test
    void getAllArticles() {
        // given
        ArticleSaveRequest articleSaveRequest1 = new ArticleSaveRequest("title1", "writer1", "contents1");
        ArticleSaveRequest articleSaveRequest2 = new ArticleSaveRequest("title2", "writer2", "contents2");
        Long id1 = articleService.saveArticle(articleSaveRequest1);
        Long id2 = articleService.saveArticle(articleSaveRequest2);

        // when
        List<ArticleResponse> articles = articleService.getAllArticles();

        // then
        assertThat(articles.size()).isEqualTo(2);
        assertThat(articles.get(0).getId()).isEqualTo(id1);
        assertThat(articles.get(1).getId()).isEqualTo(id2);
        assertThat(articles.get(0).getTitle()).isEqualTo("title1");
        assertThat(articles.get(1).getTitle()).isEqualTo("title2");
    }

    @DisplayName("id가 일치하는 article을 찾지 못했을 때 예외가 발생하는지 확인하는 테스트")
    @Test
    void findById() {
        // given
        ArticleSaveRequest articleSaveRequest1 = new ArticleSaveRequest("title1", "writer1", "contents1");
        ArticleSaveRequest articleSaveRequest2 = new ArticleSaveRequest("title2", "writer2", "contents2");
        Long id1 = articleService.saveArticle(articleSaveRequest1);
        Long id2 = articleService.saveArticle(articleSaveRequest2);

        // when
        // then
        assertThatThrownBy(() -> articleService.findById(id2 + 1L))
                .isInstanceOf(ArticleNotFoundException.class)
                .hasMessage("존재하지 않는 게시글입니다.");
    }
}
