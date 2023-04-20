package kr.codesqaud.cafe.app.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import kr.codesqaud.cafe.app.user.controller.dto.UserLoginRequest;
import kr.codesqaud.cafe.app.user.controller.dto.UserResponse;
import kr.codesqaud.cafe.app.user.controller.dto.UserSavedRequest;
import kr.codesqaud.cafe.app.user.repository.UserRepository;
import kr.codesqaud.cafe.errors.errorcode.UserErrorCode;
import kr.codesqaud.cafe.errors.response.ErrorResponse;
import kr.codesqaud.cafe.errors.response.ErrorResponse.ValidationError;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private MockHttpSession session;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() throws Exception {
        objectMapper = new ObjectMapper();
        session = new MockHttpSession();
        createSampleUser("yonghwan1107", "yonghwan1107", "김용환", "yonghwan1107@naver.com");
    }

    @AfterEach
    public void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("올바른 유저아이디와 올바른 패스워드가 주어지고 로그일할때 세션 저장소에 회원정보가 저장되는지 테스트")
    public void login_success() throws Exception {
        //given
        String userId = "yonghwan1107";
        String password = "yonghwan1107";
        String url = "/login";
        UserLoginRequest dto = new UserLoginRequest(userId, password);

        //when
        mockMvc.perform(post(url)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(redirectedUrl("/"));
        //then
        UserResponse user = (UserResponse) session.getAttribute("user");
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getName()).isEqualTo("김용환");
        assertThat(user.getEmail()).isEqualTo("yonghwan1107@naver.com");
    }

    @Test
    @DisplayName("올바른 유저아이디와 틀린 비밀번호가 주어지고 로그인할때 비밀번호 불일치로 에러 응답을 받는지 테스트")
    public void login_fail1() throws Exception {
        //given
        String userId = "yonghwan1107";
        String password = "useaweiofjaw";
        String url = "/login";
        UserLoginRequest dto = new UserLoginRequest(userId, password);
        //when
        String jsonErrorResponse = mockMvc.perform(post(url)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        ErrorResponse actual = objectMapper.readValue(jsonErrorResponse, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(
            UserErrorCode.NOT_MATCH_LOGIN.name(),
            HttpStatus.BAD_REQUEST,
            UserErrorCode.NOT_MATCH_LOGIN.getMessage(),
            null);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("부적절한 입력 형식의 아이디와 패스워드가 주어지고 로그인할때 에러 응답을 받는지 테스트")
    public void login_fail2() throws Exception {
        //given
        String userId = "";
        String password = "";
        String url = "/login";
        UserLoginRequest dto = new UserLoginRequest(userId, password);
        //when
        String jsonErrors = mockMvc.perform(post(url)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError("password", "8~16자 영문 대 소문자, 숫자, 특수문자만 사용 가능합니다."));
        errors.add(new ValidationError("userId", "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다."));

        ErrorResponse errorResponse = objectMapper.readValue(jsonErrors, ErrorResponse.class);
        Assertions.assertThat(errorResponse.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(errorResponse.getErrorMessage()).isEqualTo("유효하지 않은 입력 형식입니다.");
        Assertions.assertThat(errorResponse.getErrors()).containsAll(errors);
    }

    @Test
    @DisplayName("서버에 없는 회원 아이디가 주어지고 로그인할때 에러 응답을 받는지 테스트")
    public void login_fail3() throws Exception {
        //given
        String userId = "user10";
        String password = "user10user10";
        String url = "/login";
        UserLoginRequest dto = new UserLoginRequest(userId, password);
        //when
        String jsonErrorResponse = mockMvc.perform(post(url)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        ErrorResponse actual = objectMapper.readValue(jsonErrorResponse, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(
            UserErrorCode.NOT_MATCH_LOGIN.name(),
            HttpStatus.BAD_REQUEST,
            UserErrorCode.NOT_MATCH_LOGIN.getMessage(),
            null);
        assertThat(actual).isEqualTo(expected);
    }

    private void createSampleUser(String userId, String password, String name, String email)
        throws Exception {
        String url = "/users/new";
        UserSavedRequest dto = new UserSavedRequest(userId, password, name, email);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk());
    }

    private <T> String toJSON(T data) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(data);
    }
}
