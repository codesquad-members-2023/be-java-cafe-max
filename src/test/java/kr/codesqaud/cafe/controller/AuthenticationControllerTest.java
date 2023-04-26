package kr.codesqaud.cafe.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.dto.authentication.AccountResponse;
import kr.codesqaud.cafe.dto.authentication.SignInRequest;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.exception.member.MemberInvalidPassword;
import kr.codesqaud.cafe.service.AuthenticationService;
import kr.codesqaud.cafe.util.SignInSessionUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @DisplayName("로그인 페이지 URL를 입력하면 로그인 페이지로 이동한다")
    @Test
    void signInPage() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(get("/sign-in"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("authentication/signIn"))
            .andDo(print());
    }

    @DisplayName("이메일과 패스워드를 입력 받아서 일치하는 회원이 있을 때 로그인을 하면 홈 화면으로 이동한다")
    @Test
    void signIn() throws Exception {
        // given
        String email = "test@gmail.com";
        String password = "Test1234";
        AccountResponse accountResponse = new AccountResponse(1L, "test");
        given(authenticationService.signIn(any())).willReturn(accountResponse);

        // when

        // then
        mockMvc.perform(post("/sign-in")
                .param("email", email)
                .param("password", password)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"))
            .andDo(print());
    }

    @DisplayName("이메일과 패스워드를 입력 받아서 일치하는 회원이 없는 때 로그인을 하면 에러 메시지를 담고서 로그인 페이지로 이동한다")
    @Test
    void signInFalse() throws Exception {
        // given
        SignInRequest signInRequest = new SignInRequest("test@gmail.com", "Test1234");
        given(authenticationService.signIn(any())).willThrow(new MemberInvalidPassword(signInRequest));

        // when

        // then
        mockMvc.perform(post("/sign-in")
                .param("email", "test@gmail.com")
                .param("password", "Test1234")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(view().name("authentication/signIn"))
            .andExpect(model().attributeHasFieldErrorCode("signInRequest", "password", "Invalid"))
            .andDo(print());
    }

    @DisplayName("로그인 상태일 때 로그아웃을 하면 세션이 무효화 된다")
    @Test
    void signOut() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");

        // when

        // then
        mockMvc.perform(post("/sign-out")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"))
            .andExpect(request().sessionAttributeDoesNotExist(SignInSessionUtil.SIGN_IN_SESSION_NAME))
            .andDo(print());
    }
}
