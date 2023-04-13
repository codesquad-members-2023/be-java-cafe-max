package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.article.ArticleDTO;
import kr.codesqaud.cafe.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // ArticleController에서 잡고 있는 Bean 객체에 대해 Mock 형태의 객체를 생성해준다.
    @MockBean
    ArticleService articleService;

    @Test
    @DisplayName("postArticle 메서드를 통해 article을 저장후 메인페이지로 리다이렉트 한다.")
    void postArticleTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/article/submit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "title")
                        .param("content", "content"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("showDetailArticle 메서드를 통해 해당 id의 게시물 정보를 가져온다.")
    void showDetailArticleTest() throws Exception {

        //given
        ArticleDTO articleDto = new ArticleDTO("제목", "내용", 1L, "2023-4-10");
        given(articleService.findArticleByIdx(1)).willReturn(articleDto);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/article/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/show"))
                .andExpect(model().attributeExists("article"))//객체 검증
                .andExpect(model().attribute("article",articleDto));
    }
}