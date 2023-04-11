package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // ArticleController에서 잡고 있는 Bean 객체에 대해 Mock 형태의 객체를 생성해준다.
    @MockBean
    ArticleService articleService;

    @Test
    @DisplayName("showArticleList메서드를 통해 article의 list를 가져온후 index에 나열한다.")
    void showArticleList()throws Exception {
        //given
        List<ArticleDTO> articleList = new ArrayList<>();
        given(articleService.getArticleList()).willReturn(articleList);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("postList"))//객체 검증
                .andExpect(model().attribute("postList",articleList));
    }

}