package kr.codesqaud.cafe.app.question.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import kr.codesqaud.cafe.app.question.controller.dto.QuestionSavedRequest;
import kr.codesqaud.cafe.app.question.repository.QuestionRepository;
import kr.codesqaud.cafe.app.user.controller.dto.UserSavedRequest;
import kr.codesqaud.cafe.app.user.entity.User;
import kr.codesqaud.cafe.app.user.repository.UserRepository;
import kr.codesqaud.cafe.errors.errorcode.CommonErrorCode;
import kr.codesqaud.cafe.errors.response.ErrorResponse;
import kr.codesqaud.cafe.errors.response.ErrorResponse.ValidationError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionRepository repository;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void createSampleUser() throws Exception {
        String userId = "yonghwan1107";
        String password = "yonghwan1107";
        String name = "김용환";
        String email = "yonghwan1107@gmail.com";
        String url = "/users";
        UserSavedRequest dto = new UserSavedRequest(userId, password, name, email);
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(toJSON(dto)));
    }

    @AfterEach
    public void clean() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 객체와 제목, 내용이 주어지고 글쓰기 요청시 글쓰기가 되는지 테스트")
    public void write_success() throws Exception {
        //given
        String userId = "yonghwan1107";
        User user = userRepository.findByUserId(userId).orElseThrow();
        String writer = user.getName();
        String title = "제목1";
        String content = "내용1";
        LocalDateTime writeDate = LocalDateTime.now();
        QuestionSavedRequest dto = new QuestionSavedRequest(title, content, writeDate, userId);
        String url = "/qna";
        //when
        String jsonArticle =
            mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJSON(dto)))
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
        String title = "";
        String content = "내용1";
        LocalDateTime writeDate = LocalDateTime.now();
        String userId = "user1";
        QuestionSavedRequest dto = new QuestionSavedRequest(title, content, writeDate, userId);
        String url = "/qna";
        //when
        String jsonErrors =
            mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJSON(dto)))
                .andExpect(status().isBadRequest()).andReturn().getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        //then
        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError("title", "제목은 100자 이내여야 합니다."));

        ErrorResponse actual = objectMapper.readValue(jsonErrors, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(
            CommonErrorCode.INVALID_INPUT_FORMAT.getName(),
            HttpStatus.BAD_REQUEST,
            "유효하지 않은 입력 형식입니다.",
            errors);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("비 로그인 상태에서 글쓰기 페이지 진입시 로그인 페이지로 리다이렉트 되는지 테스트")
    public void form_fail() throws Exception {
        //given
        String url = "/qna/form";
        //when & then
        mockMvc.perform(get(url)).andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/user/login"));
    }

    private <T> String toJSON(T data) throws JsonProcessingException {
        return new ObjectMapper().registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writeValueAsString(data);
    }
}
