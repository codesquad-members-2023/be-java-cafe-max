package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.exception.ArticleNotFoundException;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.impl.MemoryArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ArticleServiceTest {

    private ArticleService articleService;


    @BeforeEach
    void initArticleService(){
        ArticleRepository articleRepository = new MemoryArticleRepository();
        articleService = new ArticleService(articleRepository);
    }

    @Test
    @DisplayName("article post 성공 테스트")
    void post_test(){
        //given
        Article article = new Article("title","content");

        //when & then
        assertThatCode(() -> articleService.post(article.toDTO())).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("article 조회 성공 테스트")
    void getArticleList_success_test(){
        //given
        articleService.post(new ArticleDTO("title","content",null,null));
        articleService.post(new ArticleDTO("title","content",null,null));
        articleService.post(new ArticleDTO("title","content",null,null));

        //when & then
        assertThat(articleService.getArticleList().size() == 3).isTrue();
    }

    @Test
    @DisplayName("article 조회 실패 테스트")
    void getArticleList_fail_test(){
        //given
        articleService.post(new ArticleDTO("title","content",null,null));
        articleService.post(new ArticleDTO("title","content",null,null));
        articleService.post(new ArticleDTO("title","content",null,null));

        //when & then
        assertThat(articleService.getArticleList().size() == 4).isFalse();
    }

    @Test
    @DisplayName("저장되지 않는 아이디를 검색했을때 예외가 발생한다.")
    void findArticleById_throwException_test() {
        //given
        int id = 1;

        //when & then
        assertThatThrownBy(() -> articleService.findArticleById(id)).isInstanceOf(ArticleNotFoundException.class);
    }

//    @Test
//    @DisplayName("저장된 아이디를 검색했을때 예외가 발생하지 않는다.")
//    void findArticleById_doesNotThrowException_test() {
//        //given
//        articleService.post(new ArticleDTO("title","content",null));
//        int id = 1;
//
//        //when & then
//        assertThatCode(() -> articleService.findArticleById(id)).doesNotThrowAnyException();
//    }
}