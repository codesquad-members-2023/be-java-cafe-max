package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.UserJoinDto;
import kr.codesqaud.cafe.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void userJoinTest() throws Exception {
        String url = "http://localhost:8080/users";
        final UserJoinDto userJoinDto = new UserJoinDto("test123", "test123123", "이성빈", "sungbin@naver.com");

        given(userService.join(any(UserJoinDto.class))).willReturn(0L);

        mvc.perform(post(url)
                        .param("userId", userJoinDto.getUserId())
                        .param("password", userJoinDto.getPassword())
                        .param("name", userJoinDto.getName())
                        .param("email", userJoinDto.getEmail())
                )
                .andExpect(status().is3xxRedirection());
    }
}