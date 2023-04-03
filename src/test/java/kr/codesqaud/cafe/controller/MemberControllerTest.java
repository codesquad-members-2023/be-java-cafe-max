package kr.codesqaud.cafe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.member.MemberRepository;
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
    void beforeEah() {
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
        String email = "test@@gmail.comdf";
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
            .andExpect(model().attributeHasFieldErrorCode("signUpRequest", "email", "Email"))
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
            .andExpect(model().attributeHasFieldErrorCode("signUpRequest", "password", "Pattern"))
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
            .andExpect(model().attributeHasFieldErrorCode("signUpRequest", "nickName", "Length"))
            .andDo(print());
    }

    @DisplayName("회원 가입시 이메일이 중복인 경우 실패")
    @Test
    void signUpFalse4() throws Exception {
        // given
        memberRepository.save(new Member(null, "test@gmail.com",
            "Test1234", "test", LocalDateTime.now()));
        Long savedId2 = memberRepository.save(
            new Member(null, "test2@gmail.com",
                "Test1234", "test", LocalDateTime.now()));
        String editEmail = "test@gmail.com";
        String editPassword = "Test4444";
        String editNickName = "만두";

        // when

        // then
        mockMvc.perform(post("/members", savedId2)
                .param("email", editEmail)
                .param("password", editPassword)
                .param("nickName", editNickName)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/signUp"))
            .andExpect(model().attributeHasFieldErrorCode("signUpRequest", "email", "Duplicate"))
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

    @DisplayName("회원 프로필 수정 페이지")
    @Test
    void profileEditForm() throws Exception {
        // given
        Long savedId = memberRepository.save(
            new Member( null, "test@gmail.com",
                "Test1234", "test", LocalDateTime.now()));

        // when

        // then
        mockMvc.perform(get("/members/{id}/edit", savedId))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/profileEdit"))
            .andDo(print());
    }

    @DisplayName("회원 프로필 수정 성공")
    @Test
    void editProfile() throws Exception {
        // given
        Long savedId = memberRepository.save(
            new Member( null, "test@gmail.com",
                "Test1234", "test", LocalDateTime.now()));
        String editEmail = "test2@gmail.com";
        String editPassword = "Test1234";
        String editNewPassword = "Test4444";
        String editNickName = "만두";

        // when

        // then
        mockMvc.perform(put("/members/{id}", savedId)
                .param("email", editEmail)
                .param("password", editPassword)
                .param("newPassword", editNewPassword)
                .param("nickName", editNickName)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/members/{id}"))
            .andDo(print());
    }

    @DisplayName("회원 프로필 수정시 이메일이 중복인 경우 실패")
    @Test
    void editProfileFalse() throws Exception {
        // given
        memberRepository.save(new Member(null, "test@gmail.com",
                "Test1234", "test", LocalDateTime.now()));
        Long savedId2 = memberRepository.save(
            new Member( null, "test2@gmail.com",
                "Test1234", "test", LocalDateTime.now()));
        String editEmail = "test@gmail.com";
        String editPassword = "Test1234";
        String editNewPassword = "Test4444";
        String editNickName = "만두";

        // when

        // then
        mockMvc.perform(put("/members/{id}", savedId2)
                .param("email", editEmail)
                .param("password", editPassword)
                .param("newPassword", editNewPassword)
                .param("nickName", editNickName)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/profileEdit"))
            .andExpect(model().attributeHasFieldErrorCode("profileEditRequest", "email", "Duplicate"))
            .andDo(print());
    }

    @DisplayName("회원 프로필 수정시 기존 비밀번호가 다를 경우 실패")
    @Test
    void editProfileFalse2() throws Exception {
        // given
        memberRepository.save(new Member(null, "test@gmail.com",
            "Test1234", "test", LocalDateTime.now()));
        Long savedId2 = memberRepository.save(
            new Member( null, "test2@gmail.com",
                "Test1234", "test", LocalDateTime.now()));
        String editEmail = "test2@gmail.com";
        String editPassword = "Test4444";
        String editNewPassword = "Test4444";
        String editNickName = "만두";

        // when

        // then
        mockMvc.perform(put("/members/{id}", savedId2)
                .param("email", editEmail)
                .param("password", editPassword)
                .param("newPassword", editNewPassword)
                .param("nickName", editNickName)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/profileEdit"))
            .andExpect(model().attributeHasFieldErrorCode("profileEditRequest", "password", "NotMatch"))
            .andDo(print());
    }
}
