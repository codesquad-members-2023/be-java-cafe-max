package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.user.UserJoinDto;
import kr.codesqaud.cafe.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void userJoinTest() throws Exception {
        String url = "http://localhost:8080/users";
        final UserJoinDto userJoinDto = new UserJoinDto("test123", "test123123", "이성빈", "sungbin@naver.com");

        BDDMockito.given(userService.join(ArgumentMatchers.any(UserJoinDto.class))).willReturn(0L);

        mvc.perform(MockMvcRequestBuilders.post(url)
                        .param("userId", userJoinDto.getUserId())
                        .param("password", userJoinDto.getPassword())
                        .param("name", userJoinDto.getName())
                        .param("email", userJoinDto.getEmail())
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}