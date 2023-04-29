package kr.codesqaud.cafe.controller;

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

import java.time.LocalDateTime;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberJoinRequestDto;
import kr.codesqaud.cafe.dto.member.MemberLoginRequestDto;
import kr.codesqaud.cafe.dto.member.MemberResponseDto;
import kr.codesqaud.cafe.dto.member.ProfileEditRequestDto;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.service.MemberService;
import kr.codesqaud.cafe.session.LoginMemberSession;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
                .andExpect(view().name("member/members"))
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
                .andExpect(redirectedUrl("/members"))
                .andDo(print());

        MemberResponseDto memberResponseDto = memberService.findByEmail(basicMemberData().getEmail());

        assertAll(
                () -> assertNotNull(memberResponseDto),
                () -> assertEquals("test@test.com", memberResponseDto.getEmail()),
                () -> assertEquals("차차", memberResponseDto.getNickname()),
                () -> assertEquals("testtest", memberResponseDto.getPassword())
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
                .andExpect(redirectedUrl("/members"))
                .andExpect(model().attributeExists("memberJoinRequestDto"));
    }

    @Test
    void login() throws Exception {
        //given
        memberService.join(basicMemberJoinRequestDtoData());
        String email = basicMemberJoinRequestDtoData().getEmail();
        String password = basicMemberJoinRequestDtoData().getPassword();

        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto(email, password);
        //when
        mockMvc.perform(post("/members/login")
                        .param("email", memberLoginRequestDto.getEmail())
                        .param("password", memberLoginRequestDto.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"))
                .andDo(print());


    }

    @Test
    void loginForm() throws Exception {
        memberService.join(basicMemberJoinRequestDtoData());

        mockMvc.perform(get("/members/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/login"))
                .andDo(print());
    }


    @Test
    void logout() throws Exception {
        // given
        memberService.join(basicMemberJoinRequestDtoData());
        String email = basicMemberJoinRequestDtoData().getEmail();
        String password = basicMemberJoinRequestDtoData().getPassword();
        ;

        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto(email, password);

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
    }

    @Test
    void profile() throws Exception {
        Long memberId = memberService.join(basicMemberJoinRequestDtoData());
        String email = basicMemberJoinRequestDtoData().getEmail();
        String password = basicMemberJoinRequestDtoData().getPassword();
        String nickname = basicMemberJoinRequestDtoData().getNickname();

        ProfileEditRequestDto profileEditRequestDto = new ProfileEditRequestDto(memberId, email, password, nickname);

        //when
        mockMvc.perform(put("/members/{email}/profile", basicMemberData().getEmail())
                        .param("memberId", String.valueOf(profileEditRequestDto.getMemberId()))
                        .param("email", profileEditRequestDto.getEmail())
                        .param("password", profileEditRequestDto.getPassword())
                        .param("nickname", profileEditRequestDto.getNickname()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/members/{email}"));
    }

    @Test
    void profileEditForm() throws Exception {
        // when,then
        mockMvc.perform(get("/members/{email}/profile", basicMemberData().getEmail())
                        .sessionAttr("loginMember", new LoginMemberSession("test@test.com", 0L)))
                .andExpect(status().isOk())
                .andExpect(view().name("member/profileEdit"))
                .andDo(print());
    }


    @Test
    @DisplayName("/put 요청시 db에서 회원 프로필을 수정한다.")
    void editProfile() throws Exception {
        //given
        Long memberId = memberService.join(basicMemberJoinRequestDtoData());
        String password = "testtesttest";
        String nickname = "피오니";

        ProfileEditRequestDto editRequestDto = new ProfileEditRequestDto(memberId, basicMemberData().getEmail(), password, nickname);
        MemberResponseDto updatedMember = memberService.findById(memberId);

        // when
        mockMvc.perform(put("/members/{email}/profile", basicMemberData().getEmail())
                        .param("memberId", String.valueOf(editRequestDto.getMemberId()))
                        .param("email", editRequestDto.getEmail())
                        .param("nickname", editRequestDto.getNickname())
                        .param("password", editRequestDto.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/members/{email}"));

        // then
        assertAll(
                () -> assertNotNull(updatedMember),
                () -> assertEquals("피오니", updatedMember.getNickname()),
                () -> assertEquals("testtesttest", updatedMember.getPassword())
        );
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

    private ProfileEditRequestDto updateProfileData(Long memberId) {
        String email = "test@test.com";
        String password = "testtesttest";
        String nickname = "피오니";
        return new ProfileEditRequestDto(memberId, email, password, nickname);
    }

}
