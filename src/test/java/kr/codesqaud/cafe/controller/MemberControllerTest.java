package kr.codesqaud.cafe.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.List;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @DisplayName("회원 가입 성공")
    @Test
    void signUp() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@gmail.com", "Test1234", "만두");
        given(memberService.signUp(any())).willReturn(1L);

        // when

        // then
        mockMvc.perform(post("/members")
                .param("email", signUpRequest.getEmail())
                .param("password", signUpRequest.getPassword())
                .param("nickName", signUpRequest.getNickName())
                .param("createDate", signUpRequest.getCreateDate().toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/members"))
            .andDo(print());
    }

    @DisplayName("회원 가입시 이메일이 빈값이거나 형식이 틀릴 경우 실패")
    @ParameterizedTest
    @CsvSource(value = {":NotBlank", "test@@naver.com:Email", "test@naver.,com:Email"}, delimiterString = ":")
    void signUpFalse(String email, String error) throws Exception {
        // given
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
            .andExpect(model().attributeHasFieldErrorCode("signUpRequest", "email", error))
            .andDo(print());
    }

    @DisplayName("회원 가입시 비밀번호가 빈값이거나 대문자,소문자,숫자가 각각 1개 이상이며 최소 8글자 미만이거나 32글자 초과인 경우 실패")
    @ParameterizedTest
    @CsvSource(value = {",NotBlank", "TEST1234,Pattern", "test1234,Pattern", "Test123,Pattern", "TestTestTestTestTestTestTestTest1,Pattern"})
    void signUpFalse2(String password, String error) throws Exception {
        // given
        String email = "test@gmail.com";
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
            .andExpect(model().attributeHasFieldErrorCode("signUpRequest", "password", error))
            .andDo(print());
    }

    @DisplayName("회원 가입시 닉네임이 빈값이거나 2글자 미만 또는 10글자 초과하는 경우 실패")
    @ParameterizedTest
    @CsvSource(value = {",NotBlank", "가,Length", "가나다라마바사아자카차,Length"})
    void signUpFalse3(String nickName, String error) throws Exception {
        // given
        String email = "test@gmail.com";
        String password = "Test1234";

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
            .andExpect(model().attributeHasFieldErrorCode("signUpRequest", "nickName", error))
            .andDo(print());
    }

    @DisplayName("회원 가입시 이메일이 중복인 경우 실패")
    @Test
    void signUpFalse4() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@gmail.com", "Test1234", "만두");
        given(memberService.isDuplicateEmail(signUpRequest.getEmail()))
            .willReturn(true);
        String email = "test@gmail.com";
        String password = "Test4444";
        String nickName = "만두2";

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
            .andExpect(model().attributeHasFieldErrorCode("signUpRequest", "email", "Duplicate"))
            .andDo(print());

    }

    @DisplayName("회원 목록 조회")
    @Test
    void findAll() throws Exception {
        // given
        List<MemberResponse> memberResponses = List.of(new MemberResponse(1L, "test@nave.com",
            "만두", LocalDateTime.now()));
        given(memberService.findAll()).willReturn(memberResponses);

        // when

        // then
        mockMvc.perform(get("/members"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/members"))
            .andExpect(model().attribute("memberResponses", memberResponses))
            .andDo(print());
    }

    @DisplayName("회원 프로필 수정 페이지")
    @Test
    void profileEditForm() throws Exception {
        // given
        Long savedId = 1L;
        given(memberService.findById(savedId)).willReturn(new MemberResponse(savedId, "test@nave.com",
            "만두", LocalDateTime.now()));

        // when

        // then
        mockMvc.perform(get("/members/{id}/edit", savedId))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/profileEdit"))
            .andExpect(model().attributeExists("profileEditRequest"))
            .andDo(print());
    }

    @DisplayName("회원 프로필 수정 성공")
    @Test
    void editProfile() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(put("/members/{id}", 1L)
                .param("email", "test2@gmail.com")
                .param("password", "Test1234")
                .param("newPassword", "Test4444")
                .param("nickName", "만두")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/members/{id}"))
            .andDo(print());
    }

    @DisplayName("회원 프로필 수정시 이메일이 중복인 경우 실패")
    @Test
    void editProfileFalse() throws Exception {
        // given
        ProfileEditRequest profileEditRequest = new ProfileEditRequest(1L, "test@gmail.com",
            "Test1234", "Mandu1234", "mandu");
        given(memberService.isDuplicateEmailAndId(profileEditRequest.getEmail(), profileEditRequest.getId()))
            .willReturn(true);

        // when

        // then
        mockMvc.perform(put("/members/{id}", profileEditRequest.getId())
                .param("email", profileEditRequest.getEmail())
                .param("password", profileEditRequest.getPassword())
                .param("newPassword", profileEditRequest.getNewPassword())
                .param("nickName", profileEditRequest.getNickName())
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
        ProfileEditRequest profileEditRequest = new ProfileEditRequest(1L, "test@gmail.com",
            "Test1234", "Mandu1234", "mandu");
        given(memberService.isNotSamePassword(profileEditRequest.getId(), profileEditRequest.getPassword()))
            .willReturn(true);

        // when

        // then
        mockMvc.perform(put("/members/{id}", profileEditRequest.getId())
                .param("email", profileEditRequest.getEmail())
                .param("password", profileEditRequest.getPassword())
                .param("newPassword", profileEditRequest.getNewPassword())
                .param("nickName", profileEditRequest.getNickName())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/profileEdit"))
            .andExpect(model().attributeHasFieldErrorCode("profileEditRequest", "password", "NotMatch"))
            .andDo(print());
    }
}
