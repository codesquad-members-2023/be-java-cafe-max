package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.config.Session;
import kr.codesqaud.cafe.controller.dto.LoginDTO;
import kr.codesqaud.cafe.controller.dto.user.UserDTO;
import kr.codesqaud.cafe.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(SignInController.class)
class SignInControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    private MockHttpSession httpSession;

    @BeforeEach
    public void setUp() {
        httpSession = new MockHttpSession();
        Session session = new Session("id", "testUser");
        httpSession.setAttribute(Session.LOGIN_USER, session);
    }


    @Test
    @DisplayName("로그인성공시 id와 nickName을 session에 저장한다.")
    void userLogin() throws Exception {
        //given
        LoginDTO loginDto = new LoginDTO("id", "user");
        UserDTO userDto = new UserDTO("nickName", "aaa@naver.com", "password", "id");
        given(userService.getUserById(loginDto.getUserId())).willReturn(userDto);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user/sign-in-success")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("userId", "id")
                        .param("password", "password123"))

                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/sign-in-success/id"));

        MvcResult mvcResult = resultActions.andReturn();
        MockHttpSession resultSession = (MockHttpSession) mvcResult.getRequest().getSession();
        Session session = (Session) resultSession.getAttribute(Session.LOGIN_USER);

        Assertions.assertThat(session.getId().equals(userDto.getId())).isTrue();
        Assertions.assertThat(session.getNickName().equals(userDto.getNickName())).isTrue();
    }

    @Test
    @DisplayName("로그인성공시 로그인 성공창을 보여준다.")
    void showLoginSuccessForm() throws Exception {
        //given
        UserDTO userDto = new UserDTO("nickName", "aaa@naver.com", "password123", "id");
        given(userService.getUserById(userDto.getId())).willReturn(userDto);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/user/sign-in-success/id")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .session(httpSession))
                //then
                .andExpect(status().isOk())
                .andExpect(view().name("user/login_success"))
                .andExpect(model().attribute("user", userDto));
    }

    @Test
    @DisplayName("로그아웃시 세션을 invalidate 시킨다.")
    void userSignOut() throws Exception {
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/user/sign-out")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .session(httpSession))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        Assertions.assertThat(httpSession.isInvalid()).isTrue();
    }
}