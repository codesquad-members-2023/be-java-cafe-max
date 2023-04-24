package kr.codesqaud.cafe.app.question.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import kr.codesqaud.cafe.app.question.controller.dto.QuestionResponse;
import kr.codesqaud.cafe.app.question.controller.dto.QuestionSavedRequest;
import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.app.question.service.QuestionService;
import kr.codesqaud.cafe.app.user.controller.dto.UserLoginRequest;
import kr.codesqaud.cafe.app.user.controller.dto.UserSavedRequest;
import kr.codesqaud.cafe.app.user.entity.User;
import kr.codesqaud.cafe.app.user.service.UserService;
import kr.codesqaud.cafe.errors.response.ErrorResponse.ValidationError;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    private MockHttpSession httpSession;

    private Long userId;

    private Long questionId;

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        httpSession = new MockHttpSession();
        userId = signUp("yonghwan1107", "yonghwan1107", "김용환", "yonghwan1107@gmail.com");
        questionId = writeQuestion();
    }


    public Long writeQuestion() {
        User user = userService.findUser("yonghwan1107");
        String title = "제목1";
        String content = "내용1";
        QuestionSavedRequest dto = new QuestionSavedRequest(title, content, user.getUserId());
        return questionService.write(dto.toEntity(user.getId())).getId();
    }

    @Test
    @DisplayName("회원 객체와 제목, 내용이 주어지고 글쓰기 요청시 글쓰기가 되는지 테스트")
    public void write_success() throws Exception {
        //given
        login("yonghwan1107", "yonghwan1107");
        String userId = "yonghwan1107";
        User user = userService.findUser(userId);
        String writer = user.getName();
        String title = "제목1";
        String content = "내용1";
        QuestionSavedRequest dto = new QuestionSavedRequest(title, content, userId);
        String url = "/qna";
        //when
        String jsonArticle =
            mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJSON(dto))
                    .session(httpSession))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        TypeReference<HashMap<String, Object>> typeReference = new TypeReference<>() {
        };
        HashMap<String, Object> questionMap = objectMapper.readValue(jsonArticle, typeReference);
        assertThat(questionMap.get("writer")).isEqualTo(writer);
        assertThat(questionMap.get("title")).isEqualTo(title);
        assertThat(questionMap.get("content")).isEqualTo(content);
    }

    @Test
    @DisplayName("부적절한 입력 형식의 제목이 주어지고 글쓰기 요청시 에러 응답을 받는지 테스트")
    public void write_fail1() throws Exception {
        //given
        login("yonghwan1107", "yonghwan1107");
        User user = userService.findUser(userId);
        String title = "";
        String content = "내용1";
        String userId = user.getUserId();
        QuestionSavedRequest dto = new QuestionSavedRequest(title, content, userId);
        String url = "/qna";
        //when
        String jsonErrors =
            mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJSON(dto))
                    .session(httpSession))
                .andExpect(status().isBadRequest()).andReturn().getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        //then
        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError("title", "제목은 100자 이내여야 합니다."));

        TypeReference<HashMap<String, Object>> typeReference = new TypeReference<>() {
        };
        HashMap<String, Object> errorMap = objectMapper.readValue(jsonErrors, typeReference);
        Assertions.assertThat(errorMap.get("httpStatus")).isEqualTo("BAD_REQUEST");
        Assertions.assertThat(errorMap.get("name")).isEqualTo("INVALID_INPUT_FORMAT");
        Assertions.assertThat(errorMap.get("errorMessage")).isEqualTo("유효하지 않은 입력 형식입니다.");
    }

    @Test
    @DisplayName("비 로그인 상태에서 글쓰기 페이지 진입시 로그인 페이지로 리다이렉트 되는지 테스트")
    public void form_fail() throws Exception {
        //given
        String url = "/qna/new";
        //when & then
        mockMvc.perform(get(url)).andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login"));
    }

    @Test
    @DisplayName("로그인한 회원이 특정 질문 조회 페이지로 접근했을때 페이지로 리다이렉션 되는지 테스트")
    public void detail_success() throws Exception {
        //given
        login("yonghwan1107", "yonghwan1107");
        String url = "/qna/" + questionId;
        //when
        QuestionResponse question = (QuestionResponse) Objects.requireNonNull(
            mockMvc.perform(get(url)
                    .session(httpSession))
                .andExpect(status().isOk())
                .andReturn().getModelAndView()).getModelMap().get("question");
        //then
        assertThat(question.getTitle()).isEqualTo("제목1");
        assertThat(question.getContent()).isEqualTo("내용1");
        assertThat(question.getWriter()).isEqualTo("김용환");
    }

    @Test
    @DisplayName("비 로그인 상태에서 특정 게시물을 접속하려고 할때 로그인 페이지로 이동되는지 테스트")
    public void detail_fail1() throws Exception {
        //given
        String url = "/qna/1";
        //when & then
        mockMvc.perform(get(url))
            .andExpect(redirectedUrl("/login"));
    }

    @Test
    @DisplayName("수정된 제목과 내용이 주어질때 질문 게시글 수정 요청시 수정이 되는지 테스트")
    public void edit_success() throws Exception {
        //given
        login("yonghwan1107", "yonghwan1107");
        String str_userId = userService.findUser(userId).getUserId();
        String modifiedTitle = "변경된 제목1";
        String modifiedContent = "변경된 내용1";
        QuestionSavedRequest dto = new QuestionSavedRequest(modifiedTitle, modifiedContent,
            str_userId);
        String url = "/qna/" + questionId;
        //when
        String json = mockMvc.perform(put(url)
                .content(toJSON(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .session(httpSession))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        TypeReference<HashMap<String, Object>> typeReference = new TypeReference<>() {
        };
        HashMap<String, Object> questionMap = objectMapper.readValue(json, typeReference);
        assertThat(questionMap.get("title")).isEqualTo("변경된 제목1");
        assertThat(questionMap.get("content")).isEqualTo("변경된 내용1");
    }

    @Test
    @DisplayName("부적절한 제목 입력 형식이 주어지고 질문 수정 요청시 에러 응답하는지 테스트")
    public void edit_fail1() throws Exception {
        //given
        login("yonghwan1107", "yonghwan1107");
        Long id = write("제목1", "내용1");
        QuestionSavedRequest dto = new QuestionSavedRequest("", "변경된 내용1", "yonghwan1107");
        String url = "/qna/" + id;
        //when
        String json = mockMvc.perform(put(url)
                .content(toJSON(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .session(httpSession))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        TypeReference<HashMap<String, Object>> typeReference = new TypeReference<>() {
        };
        HashMap<String, Object> errorMap = objectMapper.readValue(json, typeReference);
        Assertions.assertThat(errorMap.get("httpStatus")).isEqualTo("BAD_REQUEST");
        Assertions.assertThat(errorMap.get("name")).isEqualTo("INVALID_INPUT_FORMAT");
        Assertions.assertThat(errorMap.get("errorMessage")).isEqualTo("유효하지 않은 입력 형식입니다.");
    }

    @Test
    @DisplayName("본인의 질문 게시글을 삭제 요청시 삭제되고 삭제된 질문 데이터를 응답받는지 테스트")
    public void delete_success() throws Exception {
        //given
        login("yonghwan1107", "yonghwan1107");
        Long id = write("제목1", "내용1");
        String url = "/qna/" + id;
        //when & then
        mockMvc.perform(delete(url)
                .session(httpSession))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("다른 사람으로 로그인 후 다른 사람의 질문 게시글을 삭제 요청할때 에러 응답을 받는지 테스트")
    public void delete_fail1() throws Exception {
        //given
        Long id = write("제목1", "내용1");
        signUp("kim1107", "kim1107kim1107", "kim", "kim1107@naver.com");
        login("kim1107", "kim1107kim1107");
        String url = "/qna/" + id;
        //when
        mockMvc.perform(delete(url)
                .session(httpSession))
            .andExpect(status().isForbidden());
        //then
    }

    @Test
    @DisplayName("클라이언트가 서버에 없는 게시물을 요청할때 전체 게시물 목록 페이지로 리다이렉션 되는지 테스트")
    public void givenNotExistQuestionId_whenListQuestion_thenRedirection() throws Exception {
        //given
        login("yonghwan1107", "yonghwan1107");
        long id = 9999L;
        String url = "/qna/" + id;
        //when
        String json = mockMvc.perform(get(url)
                .session(httpSession))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"))
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        TypeReference<HashMap<String, Object>> typeReference = new TypeReference<>() {
        };
        HashMap<String, Object> errorMap = objectMapper.readValue(json, typeReference);
        Assertions.assertThat(errorMap.get("httpStatus")).isEqualTo("MOVED_PERMANENTLY");
        Assertions.assertThat(errorMap.get("name")).isEqualTo("NOT_FOUND_QUESTION");
        Assertions.assertThat(errorMap.get("errorMessage")).isEqualTo("게시물을 찾을 수 없습니다.");
        Assertions.assertThat(errorMap.get("redirectUrl")).isEqualTo("/");
    }

    private Long signUp(String userId, String password, String name, String email) {
        return userService.signUp(new UserSavedRequest(userId, password, name, email)).getId();
    }

    private void login(String userId, String password) throws Exception {
        mockMvc.perform(post("/login")
            .content(toJSON(new UserLoginRequest(userId, password)))
            .contentType(MediaType.APPLICATION_JSON)
            .session(httpSession));
    }

    private Long write(String title, String content) {
        return questionService.write(
                new Question(null, title, content, LocalDateTime.now(), LocalDateTime.now(), userId))
            .getId();
    }

    private <T> String toJSON(T data) throws JsonProcessingException {
        return new ObjectMapper().registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writeValueAsString(data);
    }
}
