package kr.codesqaud.cafe.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

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
        String password = "pwd1";
        String name = "홍길동";
        String email = "user1@gmail.com";
        String url = "/user/create";

        //when
        mockMvc.perform(post(url)
                .param("userId", userId)
                .param("password", password)
                .param("name", name)
                .param("email", email))
            .andExpect(status().is3xxRedirection());
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
        String password = "pwd1";
        String name = "홍길동";
        String email = "user1@gmail.com";
        String url = "/user/create";
        mockMvc.perform(post(url)
            .param("userId", userId)
            .param("password", password)
            .param("name", name)
            .param("email", email));
        //when
        MockHttpServletResponse response = mockMvc.perform(post(url)
                .param("userId", userId)
                .param("password", password)
                .param("name", name)
                .param("email", email))
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
        String password = "pwd1";
        String name = "홍길동";
        String email = "user1@gmail.com";
        String url = "/user/create";
        mockMvc.perform(post(url)
            .param("userId", userId)
            .param("password", password)
            .param("name", name)
            .param("email", email));
        //when
        MockHttpServletResponse response = mockMvc.perform(post(url)
                .param("userId", "user2")
                .param("password", password)
                .param("name", name)
                .param("email", email))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        //then
        Map map = new ObjectMapper()
            .readValue(response.getContentAsString(StandardCharsets.UTF_8), Map.class);
        Assertions.assertThat(map.get("errorCode")).isEqualTo(601);
        Assertions.assertThat(map.get("httpStatus")).isEqualTo("OK");
        Assertions.assertThat(map.get("errorMessage")).isEqualTo("이미 존재하는 이메일입니다.");
    }
}
