package kr.codesqaud.cafe.app.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kr.codesqaud.cafe.app.user.controller.dto.UserResponse;
import kr.codesqaud.cafe.app.user.controller.dto.UserSavedRequest;
import kr.codesqaud.cafe.app.user.entity.User;
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

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

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
    @DisplayName("올바른 회원정보가 주어지고 회원가입 요청시 회원가입이 되는지 테스트")
    public void signup_success() throws Exception {
        //given
        String userId = "user1";
        String password = "user1user1";
        String name = "김용일";
        String email = "user1@naver.com";
        String url = "/users";
        UserSavedRequest dto = new UserSavedRequest(userId, password, name, email);
        //when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk());
        //then
        User user = userRepository.findByUserId(userId).orElseThrow();
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("중복된 아이디가 주어지고 회원가입 요청시 에러 응답을 받는지 테스트")
    public void signup_fail1() throws Exception {
        //given
        String duplicateUserId = "yonghwan1107";
        String password = "yonghwan1107";
        String name = "김용환";
        String email = "yonghwan1107@naver.com";
        String url = "/users";
        UserSavedRequest dto = new UserSavedRequest(duplicateUserId, password, name, email);
        //when
        String jsonErrorResponse = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isConflict())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        ErrorResponse actual = objectMapper.readValue(jsonErrorResponse, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(
            UserErrorCode.ALREADY_EXIST_USERID.name(),
            HttpStatus.CONFLICT,
            UserErrorCode.ALREADY_EXIST_USERID.getMessage(),
            null);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("중복된 이메일이 주어지고 회원가입 요청시 에러 응답을 받는지 테스트")
    public void signup_fail2() throws Exception {
        //given
        String userId = "kimyonghwan1107";
        String password = "yonghwan1107";
        String name = "김용환";
        String duplicatedEmail = "yonghwan1107@naver.com";
        String url = "/users";
        UserSavedRequest dto = new UserSavedRequest(userId, password, name, duplicatedEmail);
        //when
        String jsonErrorResponse = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isConflict())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        ErrorResponse actual = objectMapper.readValue(jsonErrorResponse, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(
            UserErrorCode.ALREADY_EXIST_EMAIL.name(),
            HttpStatus.CONFLICT,
            UserErrorCode.ALREADY_EXIST_EMAIL.getMessage(),
            null);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("부적절한 입력 형식의 유저아이디, 패스워드, 이름, 이메일이 주어지고 회원가입 요청시 "
        + "에러 응답 코드를 받는지 테스트")
    public void signup_fail3() throws Exception {
        //given
        String userId = "a";
        String password = "u";
        String name = "김용일!@#$";
        String email = "user1";
        UserSavedRequest dto = new UserSavedRequest(userId, password, name, email);
        String url = "/users";
        //when
        String jsonErrors = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError("password", "8~16자 영문 대 소문자, 숫자, 특수문자만 사용 가능합니다."));
        errors.add(new ValidationError("userId", "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다."));
        errors.add(new ValidationError("email",
            "(소문자 또는 숫자로 최소1글자)@(소문자 최소1글자).(소문자 2~3글자) 형식으로 사용 가능합니다."));
        errors.add(new ValidationError("name", "1~20자 영문 소문자, 한글만 사용 가능합니다."));

        ErrorResponse errorResponse = objectMapper.readValue(jsonErrors, ErrorResponse.class);
        Assertions.assertThat(errorResponse.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(errorResponse.getErrorMessage()).isEqualTo("유효하지 않은 입력 형식입니다.");
        Assertions.assertThat(errorResponse.getErrors()).containsAll(errors);

    }

    @Test
    @DisplayName("특정 회원의 id가 주어지고 회원의 프로필이 검색되는지 테스트")
    public void profile() throws Exception {
        //given
        String userId = "yonghwan1107";
        Long id = userRepository.findByUserId(userId).orElseThrow().getId();
        String url = "/users/" + id;
        //when
        UserResponse actual = (UserResponse) Objects.requireNonNull(mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andReturn().getModelAndView()).getModelMap().get("user");
        //then
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getUserId()).isEqualTo("yonghwan1107");
        assertThat(actual.getName()).isEqualTo("김용환");
        assertThat(actual.getEmail()).isEqualTo("yonghwan1107@naver.com");
    }

    @Test
    @DisplayName("비밀번호, 이름, 이메일이 주어지고 유저아이디가 주어질때 회원정보 수정이 되는지 테스트")
    public void update_success() throws Exception {
        //given
        String userId = "yonghwan1107";
        String password = "yonghwan1107";
        String modifiedName = "홍길동";
        String modifiedEmail = "yonghwan1234@naver.com";
        Long id = userRepository.findByUserId(userId).orElseThrow().getId();
        String url = "/users/" + id + "/update";
        UserSavedRequest dto = new UserSavedRequest(userId, password, modifiedName, modifiedEmail);
        //when
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto))
                .session(session))
            .andExpect(status().isOk());
        //then
        User actual = userRepository.findByUserId(userId).orElseThrow();
        assertThat(actual.getName()).isEqualTo(modifiedName);
        assertThat(actual.getPassword()).isEqualTo(password);
        assertThat(actual.getEmail()).isEqualTo(modifiedEmail);
    }

    @Test
    @DisplayName("회원 수정 이메일 중복으로 인한 테스트")
    public void update_fail() throws Exception {
        //given
        createSampleUser("user1", "user1user1", "홍길동", "user1@naver.com");
        String userId = "yonghwan1107";
        String modifiedPassword = "yonghwan1107";
        String modifiedName = "김용환";
        String modifiedEmail = "user1@naver.com";
        Long id = userRepository.findByUserId(userId).orElseThrow().getId();
        String url = "/users/" + id + "/update";
        UserSavedRequest dto = new UserSavedRequest(userId, modifiedPassword, modifiedName,
            modifiedEmail);
        //when
        String jsonErrorResponse = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isConflict())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        //then
        ErrorResponse actual = objectMapper.readValue(jsonErrorResponse, ErrorResponse.class);
        ErrorResponse expected = new ErrorResponse(
            UserErrorCode.ALREADY_EXIST_EMAIL.name(),
            HttpStatus.CONFLICT,
            UserErrorCode.ALREADY_EXIST_EMAIL.getMessage(),
            null);
        assertThat(actual).isEqualTo(expected);
    }

    private void createSampleUser(String userId, String password, String name, String email)
        throws Exception {
        String url = "/users";
        UserSavedRequest dto = new UserSavedRequest(userId, password, name, email);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk());
    }

    // TODO: 비로그인 상태에서 /user/form/1 입장시 들어가지는 문제

    private <T> String toJSON(T data) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(data);
    }
}
