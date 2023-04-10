package kr.codesqaud.cafe.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.domain.user.UserRepository;
import kr.codesqaud.cafe.web.dto.user.UserLoginRequestDto;
import kr.codesqaud.cafe.web.dto.user.UserResponseDto;
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

    @BeforeEach
    public void beforeEach() throws Exception {
        session = new MockHttpSession();
        createSampleUser("yonghwan1107", "yonghwan1107", "김용환", "yonghwan1107@naver.com");
    }

    @AfterEach
    public void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    public void save_success() throws Exception {
        //given
        String userId = "user1";
        String password = "user1user1";
        String name = "김용일";
        String email = "user1@naver.com";
        String url = "/users";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, password, name, email);
        //when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk());
        //then
        User user = userRepository.findByUserId(userId).orElseThrow();
        Assertions.assertThat(user.getUserId()).isEqualTo(userId);
        Assertions.assertThat(user.getPassword()).isEqualTo(password);
        Assertions.assertThat(user.getName()).isEqualTo(name);
        Assertions.assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("회원가입 아이디 중복 테스트")
    public void save_fail1() throws Exception {
        //given
        String userId = "yonghwan1107";
        String password = "yonghwan1107";
        String name = "김용환";
        String email = "yonghwan1107@naver.com";
        String url = "/users";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, password, name, email);
        //when
        MockHttpServletResponse response =
            mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJSON(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        //then
        Map map = new ObjectMapper()
            .readValue(response.getContentAsString(StandardCharsets.UTF_8), Map.class);
        Assertions.assertThat(map.get("errorCode")).isEqualTo(600);
        Assertions.assertThat(map.get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("errorMessage")).isEqualTo("이미 존재하는 아이디입니다.");
    }

    @Test
    @DisplayName("회원가입 이메일 중복 테스트")
    public void save_fail2() throws Exception {
        //given
        String userId = "kimyonghwan1107";
        String password = "yonghwan1107";
        String name = "김용환";
        String duplicatedEmail = "yonghwan1107@naver.com";
        String url = "/users";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, password, name, duplicatedEmail);
        //when
        MockHttpServletResponse response =
            mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJSON(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        //then
        Map map = new ObjectMapper()
            .readValue(response.getContentAsString(StandardCharsets.UTF_8), Map.class);
        Assertions.assertThat(map.get("errorCode")).isEqualTo(601);
        Assertions.assertThat(map.get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("errorMessage")).isEqualTo("이미 존재하는 이메일입니다.");
    }

    @Test
    @DisplayName("회원가입시 아이디, 패스워드, 이름, 이메일의 입력 형식 검증이 되는지 테스트")
    public void validate_singUp_format() throws Exception {
        //given
        String userId = "a";
        String password = "u";
        String name = "김용일!@#$";
        String email = "user1";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, password, name, email);
        String url = "/users";
        //when
        MockHttpServletResponse response = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        //then
        Map<String, Map<String, Object>> map = new ObjectMapper().readValue(
            response.getContentAsString(StandardCharsets.UTF_8), Map.class);

        Assertions.assertThat(map.get("userId").get("errorCode")).isEqualTo(700);
        Assertions.assertThat(map.get("userId").get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("userId").get("errorMessage"))
            .isEqualTo("5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");

        Assertions.assertThat(map.get("password").get("errorCode")).isEqualTo(700);
        Assertions.assertThat(map.get("password").get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("password").get("errorMessage"))
            .isEqualTo("8~16자 영문 대 소문자, 숫자, 특수문자만 사용 가능합니다.");

        Assertions.assertThat(map.get("name").get("errorCode")).isEqualTo(700);
        Assertions.assertThat(map.get("name").get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("name").get("errorMessage"))
            .isEqualTo("1~20자 영문 소문자, 한글만 사용 가능합니다.");

        Assertions.assertThat(map.get("email").get("errorCode")).isEqualTo(700);
        Assertions.assertThat(map.get("email").get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("email").get("errorMessage"))
            .isEqualTo("(소문자 또는 숫자로 최소1글자)@(소문자 최소1글자).(소문자 2~3글자) 형식으로 사용 가능합니다.");
    }

    @Test
    @DisplayName("특정 회원의 프로필이 검색되는지 테스트")
    public void profile() throws Exception {
        //given
        Long id = userRepository.findByUserId("yonghwan1107").orElseThrow().getId();
        String url = "/users/" + id;
        //when
        UserResponseDto profile = (UserResponseDto) mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andReturn().getModelAndView().getModelMap().get("user");
        //then
        Assertions.assertThat(profile.getId()).isEqualTo(id);
        Assertions.assertThat(profile.getUserId()).isEqualTo("yonghwan1107");
        Assertions.assertThat(profile.getName()).isEqualTo("김용환");
        Assertions.assertThat(profile.getEmail()).isEqualTo("yonghwan1107@naver.com");
    }

    @Test
    @DisplayName("브라우저 URI에 DB에 없는 회원 아이디로 프로필을 보고자 할때 전체 회원 목록조회로 이동되는지 테스트")
    public void profile_fail() throws Exception {
        //given
        String url = "/users/10";
        //when
        mockMvc.perform(get(url)).andExpect(status().is3xxRedirection());
        //then
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void login_success() throws Exception {
        //given
        String userId = "yonghwan1107";
        String password = "yonghwan1107";
        String url = "/users/login";
        UserLoginRequestDto dto = new UserLoginRequestDto(userId, password);

        //when
        mockMvc.perform(post(url)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().is3xxRedirection());
        //then
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        Assertions.assertThat(user.getUserId()).isEqualTo(userId);
        Assertions.assertThat(user.getName()).isEqualTo("김용환");
        Assertions.assertThat(user.getEmail()).isEqualTo("yonghwan1107@naver.com");
    }

    @Test
    @DisplayName("로그인 비밀번호 불일치 테스트")
    public void login_fail1() throws Exception {
        //given
        String userId = "yonghwan1107";
        String password = "useaweiofjaw";
        String url = "/users/login";
        UserLoginRequestDto dto = new UserLoginRequestDto(userId, password);
        //when
        MockHttpServletResponse response = mockMvc.perform(post(url)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        //then
        Map map = new ObjectMapper()
            .readValue(response.getContentAsString(StandardCharsets.UTF_8), Map.class);
        Assertions.assertThat(map.get("errorCode")).isEqualTo(801);
        Assertions.assertThat(map.get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("errorMessage")).isEqualTo("아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("로그인 부적절한 입력 형식 테스트")
    public void login_fail2() throws Exception {
        //given
        String userId = "";
        String password = "";
        String url = "/users/login";
        UserLoginRequestDto dto = new UserLoginRequestDto(userId, password);
        //when
        MockHttpServletResponse response = mockMvc.perform(post(url)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        //then
        Map<String, Map<String, Object>> map =
            new ObjectMapper().readValue(response.getContentAsString(StandardCharsets.UTF_8),
                Map.class);

        Assertions.assertThat(map.get("userId").get("errorCode")).isEqualTo(700);
        Assertions.assertThat(map.get("userId").get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("userId").get("errorMessage"))
            .isEqualTo("5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");

        Assertions.assertThat(map.get("password").get("errorCode")).isEqualTo(700);
        Assertions.assertThat(map.get("password").get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("password").get("errorMessage"))
            .isEqualTo("8~16자 영문 대 소문자, 숫자, 특수문자만 사용 가능합니다.");
    }

    @Test
    @DisplayName("로그인 회원이 없는 경우 테스트")
    public void login_fail3() throws Exception {
        //given
        String userId = "user10";
        String password = "user10user10";
        String url = "/users/login";
        UserLoginRequestDto dto = new UserLoginRequestDto(userId, password);
        //when
        MockHttpServletResponse response = mockMvc.perform(post(url)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        //then
        Map map = new ObjectMapper()
            .readValue(response.getContentAsString(StandardCharsets.UTF_8), Map.class);
        Assertions.assertThat(map.get("errorCode")).isEqualTo(801);
        Assertions.assertThat(map.get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("errorMessage")).isEqualTo("아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("회원 수정 성공 테스트")
    public void update_success() throws Exception {
        //given
        String userId = "yonghwan1107";
        String modifiedPassword = "yonghwan1234";
        String modifiedName = "홍길동";
        String modifiedEmail = "yonghwan1234@naver.com";
        Long id = userRepository.findByUserId(userId).orElseThrow().getId();
        String url = "/users/" + id + "/update";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, modifiedPassword, modifiedName,
            modifiedEmail);
        //when
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk());
        //then
        User actual = userRepository.findByUserId(userId).orElseThrow();
        Assertions.assertThat(actual.getName()).isEqualTo(modifiedName);
        Assertions.assertThat(actual.getPassword()).isEqualTo(modifiedPassword);
        Assertions.assertThat(actual.getEmail()).isEqualTo(modifiedEmail);
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
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, modifiedPassword, modifiedName,
            modifiedEmail);
        //when
        MockHttpServletResponse response =
            mockMvc.perform(put(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJSON(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        //then
        Map map = new ObjectMapper()
            .readValue(response.getContentAsString(StandardCharsets.UTF_8), Map.class);
        Assertions.assertThat(map.get("errorCode")).isEqualTo(601);
        Assertions.assertThat(map.get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("errorMessage")).isEqualTo("이미 존재하는 이메일입니다.");
    }

    @Test
    @DisplayName("비밀번호가 맞는지 테스트")
    public void password_success() throws Exception {
        //given
        Long id = userRepository.findByUserId("yonghwan1107").orElseThrow().getId();
        String url = "/users/password/" + id;
        String password = "yonghwan1107";
        UserSavedRequestDto dto = new UserSavedRequestDto(null, password, null, null);
        //when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/user/form/" + id));
        //then
    }

    @Test
    @DisplayName("비밀번호가 틀려서 에러 메시지를 응답 받는지 테스트")
    public void password_fail() throws Exception {
        //given
        Long id = userRepository.findByUserId("yonghwan1107").orElseThrow().getId();
        String url = "/users/password/" + id;
        String password = "awioefjoawiefj";
        UserSavedRequestDto dto = new UserSavedRequestDto(null, password, null, null);
        //when
        MockHttpServletResponse response = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        //then
        Map map = new ObjectMapper()
            .readValue(response.getContentAsString(StandardCharsets.UTF_8), Map.class);
        Assertions.assertThat(map.get("errorCode")).isEqualTo(802);
        Assertions.assertThat(map.get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("errorMessage")).isEqualTo("비밀번호가 일치하지 않습니다.");
    }

    private void createSampleUser(String userId, String password, String name, String email)
        throws Exception {
        String url = "/users";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, password, name, email);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk());
    }


    private <T> String toJSON(T data) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(data);
    }
}
