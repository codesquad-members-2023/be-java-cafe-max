package kr.codesqaud.cafe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Objects;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberJoinRequestDto;
import kr.codesqaud.cafe.dto.member.MemberLoginRequestDto;
import kr.codesqaud.cafe.dto.member.ProfileEditRequestDto;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.service.MemberService;
import kr.codesqaud.cafe.session.LoginMemberSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

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
    void readMember() throws Exception {
        memberService.join(basicMemberJoinRequestDtoData());

        mockMvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andExpect(view().name("/all"))
                .andDo(print());
    }


    @Test
    @DisplayName("/post 요청시 db에 회원이 저장이 된다.(회원가입)")
    void joinMember() throws Exception {
        //when,then
        mockMvc.perform(post("/members")
                        .param("email", basicMemberData().getEmail())
                        .param("password", basicMemberData().getPassword())
                        .param("nickname", basicMemberData().getNickname())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"))
                .andDo(print());

        Member member = memberService.findByEmail(basicMemberData().getEmail());

        assertAll(
                () -> assertNotNull(member),
                () -> assertEquals(basicMemberData().getEmail(), member.getEmail()),
                () -> assertEquals(basicMemberData().getNickname(), member.getNickname()),
                () -> assertEquals(basicMemberData().getPassword(), member.getPassword())
        );
    }

    @Test
    void joinForm() throws Exception {
        MemberJoinRequestDto memberJoinRequestDto = basicMemberJoinRequestDtoData();

        mockMvc.perform(post("/members")
                .param("email", memberJoinRequestDto.getEmail())
                .param("nickname", memberJoinRequestDto.getNickname())
                .param("password", memberJoinRequestDto.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"))
                .andExpect(model().attributeExists("memberJoinRequestDto"));
    }

    @Test
    void login() throws Exception {
        //given
        memberService.join(basicMemberJoinRequestDtoData());
        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto(basicMemberJoinRequestDtoData().getEmail(), basicMemberJoinRequestDtoData().getPassword());
        //when
        mockMvc.perform(post("/members/login")
                        .param("email", memberLoginRequestDto.getEmail())
                        .param("password", memberLoginRequestDto.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andDo(print());


    }

    @Test
    void loginForm() throws Exception {
        memberService.join(basicMemberJoinRequestDtoData());

        mockMvc.perform(get("/members/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("/login"))
                .andDo(print());
    }


    @Test
    void logout() throws Exception {
        // given
        memberService.join(basicMemberJoinRequestDtoData());
        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto(basicMemberJoinRequestDtoData().getEmail(), basicMemberJoinRequestDtoData().getPassword());

        HttpSession session = mockMvc.perform(post("/members/login")
                        .param("email", memberLoginRequestDto.getEmail())
                        .param("password", memberLoginRequestDto.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andReturn()
                .getRequest()
                .getSession();

        // when,then
        mockMvc.perform(post("/members/logout")
                        .session((MockHttpSession) Objects.requireNonNull(session)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"))
                .andDo(print());

        assertNotNull(session);
        assertNotNull(session.getAttribute("loginMember"));
    }

    @Test
    void profile() throws Exception {
        Long memberId = memberService.join(basicMemberJoinRequestDtoData());
        ProfileEditRequestDto profileEditRequestDto = new ProfileEditRequestDto(memberId,basicMemberJoinRequestDtoData().getEmail(),basicMemberJoinRequestDtoData().getPassword(),basicMemberJoinRequestDtoData().getNickName());
        //when
        mockMvc.perform(put("/members/{email}/profile", basicMemberData().getEmail())
                        .param("memberId",String.valueOf(profileEditRequestDto.getMemberId()))
                        .param("email", profileEditRequestDto.getEmail())
                        .param("password", profileEditRequestDto.getPassword())
                        .param("nickname",profileEditRequestDto.getNickname()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/members/{email}/profile"));
    }

    @Test
    void profileEditForm() throws Exception {
        // given
        Long savedId = memberRepository.save(basicMemberData());

        // when,then
        mockMvc.perform(get("/members/{email}/profile", basicMemberData().getEmail())
                        .sessionAttr("loginMember", new LoginMemberSession("test@test.com")))
                .andExpect(status().isOk())
                .andExpect(view().name("member/profileEdit"))
                .andDo(print());
    }


    @Test
    @DisplayName("/put 요청시 db에서 회원 프로필을 수정한다.")
    void editProfile() throws Exception {
        //given
        Member savedMember = basicMemberData();
        Long saveMemberId = memberRepository.save(savedMember);

        String newPassword = "testtesttest";
        String newNickname = "피오니";

        //when
        mockMvc.perform(put("/members/{memberId}", saveMemberId)
                        .param("password", newPassword)
                        .param("nickname", newNickname)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void deleteId() throws Exception {
        memberService.join(basicMemberJoinRequestDtoData());

        mockMvc.perform(delete("/members/{email}", basicMemberJoinRequestDtoData().getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private MemberJoinRequestDto basicMemberJoinRequestDtoData() {
        String email = "test@test.com";
        String password = "testtest";
        String nickname = "차차";
        return new MemberJoinRequestDto(email, password, nickname);
    }

    private Member basicMemberData() {
        String email = "test@test.com";
        String password = "testtest";
        String nickname = "차차";
        LocalDateTime localDateTime = LocalDateTime.now();
        return new Member(email, password, nickname, localDateTime);
    }

}