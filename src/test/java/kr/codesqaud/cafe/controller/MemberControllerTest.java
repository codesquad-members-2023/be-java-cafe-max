package kr.codesqaud.cafe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.UUID;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.SignUpRequestDto;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.service.MemberService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void clean() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MemberController(memberService)).build();
        memberRepository.deleteAll();
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/member"))
                .andExpect(status().isOk())
                .andExpect(view().name("/all"))
                .andDo(print());
    }

    @Test
    @DisplayName("/post 요청시 db에 회원이 저장이 된다.(회원가입)")
    void signUp() throws Exception {
        //given
        String email = "test@test.com";
        String password = "testtest";
        String nickName = "chacha";

        //when,then
        mockMvc.perform(post("/member/signUp")
                        .param("email", email)
                        .param("password", password)
                        .param("nickName", nickName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void profile() throws Exception {
        Long memberId = memberRepository.save(new Member("test@test.com", "testtest", "chacha", LocalDateTime.now()));

        //when
        mockMvc.perform(get("/member/{memberId}", memberId))
                .andExpect(status().isOk())
                .andExpect(view().name("/profile"));
    }

    @Test
    @DisplayName("/put 요청시 db에서 회원 프로필을 수정한다.")
    void editProfile() throws Exception {
        //given
        Member savedMember = new Member("test@test.com", "testtest", "chacha", LocalDateTime.now());
        Long saveMemberId = memberRepository.save(savedMember);

        String newEmail = "newTest@test.com";
        String newPassword = "testtesttest";
        String newNickName = "피오니";

        //when
        mockMvc.perform(put("/member/{id}", saveMemberId)
                        .param("email", newEmail)
                        .param("password", newPassword)
                        .param("nickName", newNickName)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void profileEditForm() throws Exception {
        // given
        Long savedId = memberRepository.save(
                new Member("test@test.com", "testtest", "chacha", LocalDateTime.now()));

        // when,then
        mockMvc.perform(get("/member/{id}/edit", savedId))
                .andExpect(status().isOk())
                .andExpect(view().name("/profileEdit"))
                .andDo(print());
    }

    @Test
    void signUpForm() throws Exception {
        mockMvc.perform(get("/member/signUp"))
                .andExpect(status().isOk())
                .andExpect(view().name("/signUp"))
                .andExpect(model().attributeExists("signUpRequestDto"));
    }

    @Test
    void deleteId() throws Exception {
        SignUpRequestDto signUpRequestDto = basicMemberData();
        Long memberId = memberService.signUp(signUpRequestDto);

        mockMvc.perform(delete("/member/{id}",member.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private SignUpRequestDto basicMemberData() {
        String email = "test@test.com";
        String password = "testtest";
        String nickName = "chacha";
        return new SignUpRequestDto(email, password, nickName);
    }
}