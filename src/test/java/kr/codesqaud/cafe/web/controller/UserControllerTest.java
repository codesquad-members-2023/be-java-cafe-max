package kr.codesqaud.cafe.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.ui.ModelMap;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private MockHttpSession session;

    @BeforeEach
    public void session() {
        session = new MockHttpSession();
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
        String password = "user1user1@";
        String name = "홍길동";
        String email = "user1@gmail.com";
        String url = "/user/create";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, password, name, email);
        //when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk());
        //then
        User user = userRepository.findAll().get(0);
        Assertions.assertThat(user.getUserId()).isEqualTo(userId);
        Assertions.assertThat(user.getPassword()).isEqualTo(password);
        Assertions.assertThat(user.getName()).isEqualTo(name);
        Assertions.assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("회원가입 아이디 중복 테스트")
    public void save_fail1() throws Exception {
        //given
        String userId = "user1";
        String password = "user1user1@";
        String name = "홍길동";
        String email = "user1@gmail.com";
        String url = "/user/create";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, password, name, email);
        mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJSON(dto)));

        dto = new UserSavedRequestDto(userId, password, name, email);
        //when
        MockHttpServletResponse response =
            mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJSON(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        //then
        Map map = new ObjectMapper().readValue(response.getContentAsString(StandardCharsets.UTF_8),
            Map.class);
        Assertions.assertThat(map.get("errorCode")).isEqualTo(600);
        Assertions.assertThat(map.get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("errorMessage")).isEqualTo("이미 존재하는 아이디입니다.");
    }

    @Test
    @DisplayName("회원가입 이메일 중복 테스트")
    public void save_fail2() throws Exception {
        //given
        String userId = "user1";
        String password = "user1user1@";
        String name = "홍길동";
        String email = "user1@gmail.com";
        String url = "/user/create";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, password, name, email);
        mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJSON(dto)));

        dto = new UserSavedRequestDto("user2", password, name, email);
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
        String name = "김용환!@#$";
        String email = "user1";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, password, name, email);
        String url = "/user/create";
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
        String userId = "user1";
        String password = "user1user1";
        String name = "김용환";
        String email = "user1@naver.com";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, password, name, email);
        String url = "/user/create";
        mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJSON(dto)));
        //when
        url = "/users/1";
        ModelMap modelMap = mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andReturn().getModelAndView().getModelMap();
        //then
        UserResponseDto profile = (UserResponseDto) modelMap.getAttribute("user");
        Assertions.assertThat(profile.getId()).isEqualTo(1L);
        Assertions.assertThat(profile.getUserId()).isEqualTo("user1");
        Assertions.assertThat(profile.getName()).isEqualTo("김용환");
        Assertions.assertThat(profile.getEmail()).isEqualTo("user1@naver.com");
    }

    @Test
    @DisplayName("브라우저 URI에 DB에 없는 회원 아이디로 프로필을 보고자 할때 전체 회원 목록조회로 이동되는지 테스트")
    public void profile_fail() throws Exception {
        //given
        String url = "/users/1";
        //when
        mockMvc.perform(get(url)).andExpect(status().is3xxRedirection());
        //then
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void login_success() throws Exception {
        //given
        createSampleUser("user1", "user1user1@", "김용환", "user1@gmail.com");
        String userId = "user1";
        String password = "user1user1@";
        String url = "/user/login";
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
        Assertions.assertThat(user.getEmail()).isEqualTo("user1@gmail.com");
    }

    @Test
    @DisplayName("로그인 비밀번호 불일치 테스트")
    public void login_fail1() throws Exception {
        //given
        createSampleUser("user1", "user1user1@", "김용환", "user1@gmail.com");
        String userId = "user1";
        String password = "useaweiofjaw";
        String url = "/user/login";
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
        createSampleUser("user1", "user1user1@", "김용환", "user1@gmail.com");
        String userId = "";
        String password = "";
        String url = "/user/login";
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
        createSampleUser("user1", "user1user1@", "김용환", "user1@gmail.com");
        String userId = "user2";
        String password = "user2user2";
        String url = "/user/login";
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
        createSampleUser("user1", "user1user1@", "김용환", "user1@gmail.com");
        String userId = "user1";
        String modifiedPassword = "user2user2@";
        String modifiedName = "김용환";
        String modifiedEmail = "user1@gmail.com";
        String url = "/users/1/update";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, modifiedPassword, modifiedName,
            modifiedEmail);
        //when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().is3xxRedirection());
        //then
        User actual = userRepository.findByUserId("user1").orElseThrow();
        Assertions.assertThat(actual.getName()).isEqualTo(modifiedName);
        Assertions.assertThat(actual.getPassword()).isEqualTo(modifiedPassword);
    }

    @Test
    @DisplayName("회원 수정 이메일 중복으로 인한 테스트")
    public void update_fail() throws Exception {
        //given
        createSampleUser("user1", "user1user1@", "김용환", "user1@gmail.com");
        createSampleUser("user2", "user2user2@", "홍길동", "user2@gmail.com");

        String userId = "user1";
        String modifiedPassword = "user1user1@";
        String modifiedName = "김용환";
        String modifiedEmail = "user2@gmail.com";
        String url = "/users/1/update";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, modifiedPassword, modifiedName,
            modifiedEmail);
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

    private void createSampleUser(String userId, String password, String name, String email)
        throws Exception {
        String url = "/user/create";
        UserSavedRequestDto dto = new UserSavedRequestDto(userId, password, name, email);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호가 맞는지 테스트")
    public void password_success() throws Exception {
        //given
        createSampleUser("user1", "user1user1@", "김용환", "user1@gmail.com");
        String url = "/user/1/password";
        String password = "user1user1@";
        UserSavedRequestDto dto = new UserSavedRequestDto(null, password, null, null);
        //when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(dto)))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/users/1/form"));
        //then
    }

    @Test
    @DisplayName("비밀번호가 틀려서 에러 메시지를 응답 받는지 테스트")
    public void password_fail() throws Exception {
        //given
        createSampleUser("user1", "user1user1@", "김용환", "user1@gmail.com");
        String url = "/user/1/password";
        String password = "user1aowei";
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

    private <T> String toJSON(T data) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(data);
    }
}
