package kr.codesqaud.cafe.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.domain.user.UserRepository;
import kr.codesqaud.cafe.exception.ExceptionDto;
import kr.codesqaud.cafe.web.dto.UserResponseDto;
import kr.codesqaud.cafe.web.dto.UserSavedRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

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
        UserSavedRequestDto dto = new UserSavedRequestDto();
        dto.setUserId(userId);
        dto.setPassword(password);
        dto.setName(name);
        dto.setEmail(email);
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
        UserSavedRequestDto dto = new UserSavedRequestDto();
        dto.setUserId(userId);
        dto.setPassword(password);
        dto.setName(name);
        dto.setEmail(email);
        mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJSON(dto)));

        dto = new UserSavedRequestDto();
        dto.setUserId(userId);
        dto.setPassword(password);
        dto.setName(name);
        dto.setEmail(email);
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
        String userId = "user1";
        String password = "user1user1@";
        String name = "홍길동";
        String email = "user1@gmail.com";
        String url = "/user/create";
        UserSavedRequestDto dto = new UserSavedRequestDto();
        dto.setUserId(userId);
        dto.setPassword(password);
        dto.setName(name);
        dto.setEmail(email);
        mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJSON(dto)));

        dto = new UserSavedRequestDto();
        dto.setUserId("user2");
        dto.setPassword(password);
        dto.setName(name);
        dto.setEmail(email);
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
        UserSavedRequestDto dto = new UserSavedRequestDto();
        dto.setUserId("a");
        dto.setPassword("u");
        dto.setName("김용환!@#$");
        dto.setEmail("user1");
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
        UserSavedRequestDto dto = new UserSavedRequestDto();
        dto.setUserId("user1");
        dto.setPassword("user1user1");
        dto.setName("김용환");
        dto.setEmail("user1@naver.com");
        String url = "/user/create";
        mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJSON(dto)));
        //when
        url = "/users/user1";
        ModelMap modelMap = mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andReturn().getModelAndView().getModelMap();
        //then
        UserResponseDto profile = (UserResponseDto) modelMap.getAttribute("user");
        Assertions.assertThat(profile.getUserId()).isEqualTo("user1");
        Assertions.assertThat(profile.getName()).isEqualTo("김용환");
        Assertions.assertThat(profile.getEmail()).isEqualTo("user1@naver.com");
    }

    @Test
    @DisplayName("브라우저 URI에 DB에 없는 회원 아이디로 프로필을 보고자 할때 전체 회원 목록조회로 이동되는지 테스트")
    public void profile_fail() throws Exception {
        //given
        String url = "/users/user1";
        //when
        ExceptionDto error = (ExceptionDto) mockMvc.perform(get(url))
            .andExpect(status().is3xxRedirection())
            .andReturn().getModelAndView().getModelMap().get("error");
        //then
        Assertions.assertThat(error.getErrorCode()).isEqualTo(800);
        Assertions.assertThat(error.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    private <T> String toJSON(T data) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(data);
    }
}
