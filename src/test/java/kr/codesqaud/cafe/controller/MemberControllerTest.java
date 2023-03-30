package kr.codesqaud.cafe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import kr.codesqaud.cafe.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository.deleteAll();
    }

    @DisplayName("회원 가입 성공")
    @Test
    void signUp() throws Exception {
        // given
        String email = "test@gmail.com";
        String password = "Test1234";
        String nickName = "mandu";

        // when

        // then
        mockMvc.perform(post("/members")
                .param("email", email)
                .param("password", password)
                .param("nickName", nickName)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/members"))
            .andDo(print());
    }

    @DisplayName("회원 가입시 이메일 형식이 틀릴 경우 실패")
    @Test
    void signUpFalse() throws Exception {
        // given
        String email = "test@gmail.comdf";
        String password = "Test1234";
        String nickName = "mandu";

        // when

        // then
        mockMvc.perform(post("/members")
                .param("email", email)
                .param("password", password)
                .param("nickName", nickName)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/signUp"))
            .andExpect(model().attributeHasFieldErrors("signUpRequest", "email"))
            .andDo(print());
    }

    @DisplayName("회원 가입시 비밀번호 형식이 틀릴 경우 실패")
    @Test
    void signUpFalse2() throws Exception {
        // given
        String email = "test@gmail.com";
        String password = "test1234";
        String nickName = "mandu";

        // when

        // then
        mockMvc.perform(post("/members")
                .param("email", email)
                .param("password", password)
                .param("nickName", nickName)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/signUp"))
            .andExpect(model().attributeHasFieldErrors("signUpRequest", "password"))
            .andDo(print());
    }

    @DisplayName("회원 가입시 닉네임 형식이 틀릴 경우 실패")
    @Test
    void signUpFalse3() throws Exception {
        // given
        String email = "test@gmail.com";
        String password = "Test1234";
        String nickName = "m";

        // when

        // then
        mockMvc.perform(post("/members")
                .param("email", email)
                .param("password", password)
                .param("nickName", nickName)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/signUp"))
            .andExpect(model().attributeHasFieldErrors("signUpRequest", "nickName"))
            .andDo(print());
    }

    @DisplayName("회원 목록 조회")
    @Test
    void findAll() throws Exception {
        // given


        // when


        // then
        mockMvc.perform(get("/members"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/members"))
            .andDo(print());
    }
}
