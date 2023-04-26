package kr.codesqaud.cafe.app.comment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import kr.codesqaud.cafe.app.comment.controller.dto.CommentSavedRequest;
import kr.codesqaud.cafe.app.question.entity.Question;
import kr.codesqaud.cafe.app.question.repository.QuestionRepository;
import kr.codesqaud.cafe.app.user.controller.dto.UserResponse;
import kr.codesqaud.cafe.app.user.entity.User;
import kr.codesqaud.cafe.app.user.repository.UserRepository;
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

@AutoConfigureMockMvc
@SpringBootTest
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private MockHttpSession session;

    private Long questionId;

    private Long userId;

    @BeforeEach
    public void setup() {
        session = new MockHttpSession();
        User save = userRepository.save(
            User.builder()
                .userId("yonghwan1107")
                .password("yonghwan1107")
                .email("yonghwan1107@naver.com")
                .name("김용환")
                .build());
        session.setAttribute("user", new UserResponse(save));

        Question saveQuestion = questionRepository.save(
            Question.builder()
                .title("제목1")
                .content("내용1")
                .writer(save)
                .build());
        questionId = saveQuestion.getId();
        userId = save.getId();
    }

    @Test
    @DisplayName("댓글 내용이 주어졌을때 댓글 작성 요청시 댓글이 달아지고 해당 질문 게시글로 이동되는지 테스트")
    public void createComment_success() throws Exception {
        //given
        CommentSavedRequest dto = new CommentSavedRequest("댓글1", questionId, userId);
        String url = String.format("/qna/%d/comments", questionId);

        //when
        String json = mockMvc.perform(post(url)
                .content(toJSON(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .session(session))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        TypeReference<HashMap<String, Object>> typeReference = new TypeReference<>() {
        };
        HashMap<String, Object> map = objectMapper.readValue(json, typeReference);
        Assertions.assertThat(map.get("content")).isEqualTo("댓글1");
    }

    @Test
    @DisplayName("부적절한 댓글 내용 입력이 주어지고 댓글 작성 요청시 에러 응답하는지 테스트")
    public void createComment_fail1() throws Exception {
        //given
        String content = "";
        CommentSavedRequest dto = new CommentSavedRequest(content, questionId, userId);
        String url = String.format("/qna/%d/comments", questionId);
        //when
        String json = mockMvc.perform(post(url)
                .content(toJSON(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .session(session))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        TypeReference<HashMap<String, Object>> typeReference = new TypeReference<>() {
        };
        HashMap<String, Object> map = objectMapper.readValue(json, typeReference);
        Assertions.assertThat(map.get("httpStatus")).isEqualTo("BAD_REQUEST");
        Assertions.assertThat(map.get("name")).isEqualTo("INVALID_INPUT_FORMAT");
        Assertions.assertThat(map.get("errorMessage")).isEqualTo("유효하지 않은 입력 형식입니다.");
    }

    @Test
    @DisplayName("3000글자가 넘는 댓글 내용 입력이 주어지고 댓글 작성 요청시 에러 응답하는지 테스트")
    public void createComment_fail2() throws Exception {
        //given
        String content = "a".repeat(3001);
        CommentSavedRequest dto = new CommentSavedRequest(content, questionId, userId);
        String url = String.format("/qna/%d/comments", questionId);
        //when
        String json = mockMvc.perform(post(url)
                .content(toJSON(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .session(session))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        TypeReference<HashMap<String, Object>> typeReference = new TypeReference<>() {
        };
        HashMap<String, Object> map = objectMapper.readValue(json, typeReference);
        Assertions.assertThat(map.get("httpStatus")).isEqualTo("BAD_REQUEST");
        Assertions.assertThat(map.get("name")).isEqualTo("INVALID_INPUT_FORMAT");
        Assertions.assertThat(map.get("errorMessage")).isEqualTo("유효하지 않은 입력 형식입니다.");
    }

    private <T> String toJSON(T data) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(data);
    }
}
