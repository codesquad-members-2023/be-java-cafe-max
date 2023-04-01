package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.exception.ArticleNotFoundException;
import org.junit.jupiter.api.*;

import static kr.codesqaud.cafe.repository.impl.MemoryArticleRepository.resetSequenceForTest;

class ArticleServiceTest {

    private ArticleService articleService;

    @BeforeEach
    void initArticleService(){
        articleService = new ArticleService();
        resetSequenceForTest();
    }

    @Test
    @DisplayName("article post 성공 테스트")
    void post_test(){
        //given
        Article article = new Article("title","content");

        //when & then
        Assertions.assertDoesNotThrow(() ->  articleService.post(article));
    }

    @Test
    @DisplayName("article 조회 성공 테스트")
    void getArticleList_success_test(){
        //given
        articleService.post(new Article("title","content"));
        articleService.post(new Article("title","content"));
        articleService.post(new Article("title","content"));

        //when & then
        Assertions.assertTrue(articleService.getArticleList().size() == 3);
    }

    @Test
    @DisplayName("article 조회 실패 테스트")
    void getArticleList_fail_test(){
        //given
        articleService.post(new Article("title","content"));
        articleService.post(new Article("title","content"));
        articleService.post(new Article("title","content"));

        //when & then
        Assertions.assertFalse(articleService.getArticleList().size() == 4);
    }

    @Test
    @DisplayName("저장되지 않는 article id를 검색했을때 예외가 발생한다.")
    void findArticleById_throwException_test() {
        //given
        int id = 1;
        //when & then
        Assertions.assertThrows(ArticleNotFoundException.class,() -> articleService.findArticleById(id));
    }

    @Test
    @DisplayName("저장된 article id를 검색했을때 예외가 발생하지 않는다.")
    void findArticleById_doesNotThrowException_test() {
        Article article = new Article("title","content");
        articleService.post(article);
        System.out.println(articleService.getArticleList().get(0).getId());
        //given
        int id = 1;
        //when & then
        Assertions.assertDoesNotThrow(() -> articleService.findArticleById(id));
    }


}