package codesquad.cafe.domain.article.controller;

import codesquad.cafe.domain.article.domain.Article;
import codesquad.cafe.domain.article.dto.ArticleRequestDto;
import codesquad.cafe.domain.article.repository.MemoryArticleRepository;
import codesquad.cafe.domain.user.repository.MemoryUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemoryArticleRepository articleRepository;

    @Test
    @DisplayName("[GET] / 로 이동하면 게시글 목록 가져와서 index 화면에 출력하기 테스트")
    void showHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("posts"))
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    @DisplayName("[POST] /questions 로 이동하면 게시글 정보를 보내고 redirect:/ 로 이동하기 테스트")
    void writePost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("writer", "sio")
                        .param("title", "sio title")
                        .param("contents", "sio contents"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));
    }

    @Test
    @DisplayName("[GET] /articles/{postId} 로 이동하면 해당 게시글의 정보를 불러와 qna/show에 출력하기 테스트")
    void showDetailPost() throws Exception {
        articleRepository.save(createDummyArticle());

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/{postId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("post"))
                .andExpect(MockMvcResultMatchers.view().name("qna/show"));
    }

    private Article createDummyArticle() {
        return new Article(1L, "sio", "title", "contents", LocalDateTime.of(2023, 4, 7, 3, 42));
    }
}