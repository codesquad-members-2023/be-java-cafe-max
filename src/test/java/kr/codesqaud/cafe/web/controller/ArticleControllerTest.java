package kr.codesqaud.cafe.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.article.ArticleRepository;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.domain.user.UserRepository;
import kr.codesqaud.cafe.web.dto.article.ArticleResponseDto;
import kr.codesqaud.cafe.web.dto.article.ArticleSavedRequestDto;
import kr.codesqaud.cafe.web.dto.user.UserSavedRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository repository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void createSampleUser() throws Exception {
        String userId = "yonghwan1107";
        String password = "yonghwan1107";
        String name = "김용환";
        String email = "yonghwan1107@gmail.com";
        String url = "/users";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, password, name, email);
        mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJSON(dto)));
    }

    @AfterEach
    public void clean() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("글쓰기가 성공하는지 테스트")
    public void save_success() throws Exception {
        //given
        String userId = "yonghwan1107";
        User user = userRepository.findByUserId(userId).orElseThrow();
        String writer = user.getName();
        String title = "제목1";
        String content = "내용1";
        LocalDateTime writeDate = LocalDateTime.now();
        ArticleSavedRequestDto dto = new ArticleSavedRequestDto(title, content, writeDate, userId);
        String url = "/qna";
        //when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk());
        //then
        List<Article> all = repository.findAll();
        Article article = all.get(all.size() - 1);
        Assertions.assertThat(article.getId()).isNotNull();
        Assertions.assertThat(article.getUser().getName()).isEqualTo(writer);
        Assertions.assertThat(article.getTitle()).isEqualTo(title);
        Assertions.assertThat(article.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("글쓰기 작성자와 제목 입력 형식이 부적절한 경우 에러 코드를 응답하는지 테스트")
    public void save_fail1() throws Exception {
        //given
        String writer = "";
        String title = "";
        String content = "내용1";
        LocalDateTime writeDate = LocalDateTime.now();
        String userId = "user1";
        ArticleSavedRequestDto dto = new ArticleSavedRequestDto(title, content, writeDate, userId);
        String url = "/qna";
        //when
        MockHttpServletResponse response = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        //then
        Map<String, Map<String, Object>> map = new ObjectMapper().readValue(
            response.getContentAsString(StandardCharsets.UTF_8), Map.class);
        Assertions.assertThat(map.get("title").get("errorCode")).isEqualTo(700);
        Assertions.assertThat(map.get("title").get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("title").get("errorMessage"))
            .isEqualTo("제목은 100자 이내여야 합니다.");
    }

    @Test
    @DisplayName("게시글 조회 목록 페이지 요청시 각 게시글 정보를 가져오고 게시글의 회원 정보를 가지고 있는지 테스트")
    public void list() throws Exception {
        //given
        String url = "/";
        //when
        ModelMap modelMap = Objects.requireNonNull(mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andReturn().getModelAndView()).getModelMap();

        //then
        List<ArticleResponseDto> articles =
            (List<ArticleResponseDto>) modelMap.getAttribute("articles");
        articles.forEach(article -> {
            Assertions.assertThat(article.getUserId()).isNotNull();
        });
    }

    @Test
    @DisplayName("비 로그인 상태에서 글쓰기 페이지 진입시 로그인 페이지로 리다이렉트 되는지 테스트")
    public void form_fail() throws Exception {
        //given
        String url = "/qna/form";
        //when
        mockMvc.perform(get(url))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/user/login"));
        //then
    }

    private <T> String toJSON(T data) throws JsonProcessingException {
        return new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writeValueAsString(data);
    }
}
