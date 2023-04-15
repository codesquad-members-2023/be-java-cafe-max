package kr.codesqaud.cafe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
    void joinMember() throws Exception {
        //when,then
        mockMvc.perform(post("/member/join")
                        .param("email", basicMemberData().getEmail())
                        .param("password", basicMemberData().getPassword())
                        .param("nickName", basicMemberData().getNickName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/join"))
                .andDo(print());

        Member member = memberService.findByEmail(basicMemberData().getEmail());
        assertNotNull(member);
        assertEquals(basicMemberData().getEmail(), member.getEmail());
        assertEquals(basicMemberData().getNickName(), member.getNickName());
        assertEquals(basicMemberData().getPassword(), member.getPassword());
    }

    @Test
    void joinForm() throws Exception {
        mockMvc.perform(get("/member/join"))
                .andExpect(status().isOk())
                .andExpect(view().name("/form"))
                .andExpect(model().attributeExists("memberJoinRequestDto"));
    }

    @Test
    void login() throws Exception {
        //given
        memberService.join(basicMemberJoinRequestDtoData());
        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto(basicMemberJoinRequestDtoData().getEmail(), basicMemberJoinRequestDtoData().getPassword());

        //when
        mockMvc.perform(post("/member/login")
                        .param("email", memberLoginRequestDto.getEmail())
                        .param("password", memberLoginRequestDto.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andDo(print());


    }

    @Test
    void loginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("/login"))
                .andDo(print());
    }


    @Test
    void logout() throws Exception {
        // given
        memberService.join(basicMemberJoinRequestDtoData());
        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto(basicMemberJoinRequestDtoData().getEmail(), basicMemberJoinRequestDtoData().getPassword());

        HttpSession session = mockMvc.perform(post("/member/login")
                        .param("email", memberLoginRequestDto.getEmail())
                        .param("password", memberLoginRequestDto.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andReturn()
                .getRequest()
                .getSession();

        // when,then
        mockMvc.perform(get("/member/{memberId}/logout", 1)
                        .session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andDo(print());

        assertNotNull(session);
        assertNull(session.getAttribute("loginMember"));
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
        mockMvc.perform(put("/member/{memberId}", saveMemberId)
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
        mockMvc.perform(get("/member/{memberId}/edit", savedId))
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

        mockMvc.perform(delete("/member/{memberId}", memberId)
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