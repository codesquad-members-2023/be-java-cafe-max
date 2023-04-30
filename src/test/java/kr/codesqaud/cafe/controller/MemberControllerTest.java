package kr.codesqaud.cafe.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import kr.codesqaud.cafe.exception.member.MemberDuplicateEmailException;
import kr.codesqaud.cafe.exception.member.MemberInvalidPassword;
import kr.codesqaud.cafe.service.MemberService;
import kr.codesqaud.cafe.util.SignInSessionUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @DisplayName("로그인 했을 때 회원목록을 조회하면 전체 회원목록 페이지로 이동한다")
    @Test
    void showMembers() throws Exception {
        // given
        Long savedId = 1L;
        AccountSession accountSession = new AccountSession(savedId, "만두");
        List<MemberResponse> memberResponses = List.of(new MemberResponse(savedId, "test@nave.com",
            "만두", LocalDateTime.now()));
        given(memberService.findAll()).willReturn(memberResponses);

        // when

        // then
        mockMvc.perform(get("/members")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/members"))
            .andExpect(model().attribute("memberResponses", memberResponses))
            .andDo(print());
    }

    @DisplayName("로그인 하지 않았을 때 회원목록을 조회하면 로그인 페이지로 이동한다")
    @Test
    void showMembersFalse() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(get("/members"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/sign-in"))
            .andDo(print());
    }

    @DisplayName("로그인 한 회원과 수정할 회원이 같은 회원일 때 회원 프로필 페이지에 접근하면 회원 프로필 수정 페이지로 이동한다")
    @Test
    void showProfileEditForm() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");
        given(memberService.findProfileForEditing(1L, accountSession.getMemberId()))
            .willReturn(new ProfileEditRequest(1L, "test@nave.com", null, null, "만두"));

        // when

        // then
        mockMvc.perform(get("/members/{id}/form", 1L)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/profileEdit"))
            .andExpect(model().attributeExists("profileEditRequest"))
            .andDo(print());
    }

    @DisplayName("로그인 하지 않았을 때 회원 프로필 페이지에 접근하면 로그인 페이지로 이동한다")
    @Test
    void showProfileEditFormFalse() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(get("/members/{id}/form", 1L))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/sign-in"))
            .andDo(print());
    }

    @DisplayName("로그인 한 회원과 수정할 회원이 같은 회원일 아닐때 회원 프로필 페이지에 접근하면 에러 페이지로 이동한다")
    @Test
    void showProfileEditFormFalse2() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(2L, "만두");
        given(memberService.findProfileForEditing(1L, accountSession.getMemberId()))
            .willThrow(UnauthorizedException.class);

        // when

        // then
        mockMvc.perform(get("/members/{id}/form", 1L)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/4xx"))
            .andDo(print());
    }

    @DisplayName("로그인 한 회원과 수정할 회원이 같은 회원일 때 회원 프로필 수정하면 회원 프로필 페이지로 이동한다")
    @Test
    void editProfile() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");

        // when

        // then
        mockMvc.perform(put("/members/{id}", 1L)
                .param("email", "test2@gmail.com")
                .param("password", "Test1234")
                .param("newPassword", "Test4444")
                .param("nickname", "만두")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("/members/{id}"))
            .andDo(print());
    }

    @DisplayName("로그인 한 회원과 수정할 회원이 같은 회원이고 이메일을 입력시 중복된 이메일이 있을 때 회원 프로필 수정하면 "
        + "에러 메시지를 담고 회원 프로필 페이지로 이동한다")
    @Test
    void editProfileFalse() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");
        ProfileEditRequest profileEditRequest = new ProfileEditRequest(1L, "test@gmail.com",
            "Test1234", "Mandu1234", "mandu");
        willThrow(new MemberDuplicateEmailException(profileEditRequest)).given(memberService).update(any(), any());

        // when

        // then
        mockMvc.perform(put("/members/{id}", 1L)
                .param("email", "test@gmail.com")
                .param("password", "Test1234")
                .param("newPassword", "Mandu1234")
                .param("nickname", "mandu")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/profileEdit"))
            .andExpect(model().attributeHasFieldErrorCode("profileEditRequest", "email", "Duplicate"))
            .andDo(print());
    }

    @DisplayName("로그인 한 회원과 수정할 회원이 같은 회원이고 기존 비밀번호와 다르게 입력 했을때 회원 프로필 수정하면 "
        + "에러 메시지를 담고 회원 프로필 페이지로 이동한다")
    @Test
    void editProfileFalse2() throws Exception {
        // given
        Long id = 1L;
        AccountSession accountSession = new AccountSession(id, "만두");
        ProfileEditRequest profileEditRequest = new ProfileEditRequest(id, "test@gmail.com",
            "Test1234", "Mandu1234", "mandu");
        willThrow(new MemberInvalidPassword(profileEditRequest)).given(memberService).update(any(), any());

        // when

        // then
        mockMvc.perform(put("/members/{id}", profileEditRequest.getId())
                .param("email", "test@gmail.com")
                .param("password", "Test1234")
                .param("newPassword", "Mandu1234")
                .param("nickname", "mandu")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/profileEdit"))
            .andExpect(model().attributeHasFieldErrorCode("profileEditRequest", "password", "Invalid"))
            .andDo(print());
    }

    @DisplayName("로그인 한 회원과 수정할 회원이 다른 회원일때 회원 프로필 수정하면 에러 페이지로 이동한다")
    @Test
    void editProfileFalse3() throws Exception {
        Long id = 1L;
        AccountSession accountSession = new AccountSession(2L, "만두");
        willThrow(new UnauthorizedException()).given(memberService).update(any(), any());

        // when

        // then
        mockMvc.perform(put("/members/{id}", id)
                .param("email", "test@gmail.com")
                .param("password", "Test1234")
                .param("newPassword", "Mandu1234")
                .param("nickname", "mandu")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/4xx"))
            .andDo(print());
    }

    @DisplayName("이메일, 패스워드, 닉네임이 형식에 맞게 입력되었을 때 회원가입을 하면 홈으로 이동한다")
    @Test
    void signUp() throws Exception {
        // given
        given(memberService.signUp(any())).willReturn(1L);

        // when

        // then
        mockMvc.perform(post("/members/sign-up")
                .param("email", "test@gmail.com")
                .param("password", "Test1234")
                .param("nickname", "만두")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"))
            .andDo(print());
    }

    @DisplayName("이메일이 빈값 이거나, 형식이 틀리게 입력되었을 때 회원가입하면 에러메시지를 담고서 회원가입 폼으로 이동한다")
    @ParameterizedTest
    @CsvSource(value = {":NotBlank", "test@@naver.com:Email", "test@naver.,com:Email"}, delimiterString = ":")
    void signUpFalse(String email, String error) throws Exception {
        // given

        // when

        // then
        mockMvc.perform(post("/members/sign-up")
                .param("email", email)
                .param("password", "Test1234")
                .param("nickName", "mandu")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/signUp"))
            .andExpect(model().attributeHasFieldErrorCode("signUpRequest", "email", error))
            .andDo(print());
    }

    @DisplayName("비밀번호가 빈값이거나 대문자,소문자,숫자가 각각 1개 이상이며 최소 8글자 미만이거나 32글자 초과하게 입력되었을 때 "
        + "회원가입하면 에러메시지를 담고서 회원가입 폼으로 이동한다")
    @ParameterizedTest
    @MethodSource("provideValueForValidatorName")
    void signUpFalse2(String password, String error) throws Exception {
        // given

        // when

        // then
        mockMvc.perform(post("/members/sign-up")
                .param("email", "test@gmail.com")
                .param("password", password)
                .param("nickname", "mandu")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/signUp"))
            .andExpect(model().attributeHasFieldErrorCode("signUpRequest", "password", error))
            .andDo(print());
    }

    private static Stream<Arguments> provideValueForValidatorName() {
        return Stream.of(Arguments.of(null, "NotBlank"),
            Arguments.of("TEST1234", "Pattern"),
            Arguments.of("test1234", "Pattern"),
            Arguments.of("Test123", "Pattern"),
            Arguments.of("Test1".repeat(7), "Pattern"));
    }

    @DisplayName("닉네임이 빈값이거나 2글자 미만 또는 10글자 초과하게 입력되었을 때 회원 가입하면 에러메시지를 담고서 회원가입 폼으로 이동한다")
    @ParameterizedTest
    @CsvSource(value = {",NotBlank", "가,Length", "가나다라마바사아자카차,Length"})
    void signUpFalse3(String nickName, String error) throws Exception {
        // given

        // when

        // then
        mockMvc.perform(post("/members/sign-up")
                .param("email", "test@gmail.com")
                .param("password", "Test1234")
                .param("nickname", nickName)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/signUp"))
            .andExpect(model().attributeHasFieldErrorCode("signUpRequest", "nickname", error))
            .andDo(print());
    }

    @DisplayName("이메일이 입력 시 회원 중에 중복된 이메일이 있을 때 회원가입하면 에러메시지를 담고서 회원가입 폼으로 이동한다")
    @Test
    void signUpFalse4() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@gmail.com", "Test1234", "만두");
        given(memberService.signUp(any())).willThrow(new MemberDuplicateEmailException(signUpRequest));

        // when

        // then
        mockMvc.perform(post("/members/sign-up")
                .param("email", "test@gmail.com")
                .param("password", "Test1234")
                .param("nickname", "만두")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("member/signUp"))
            .andExpect(model().attributeHasFieldErrorCode("signUpRequest", "email", "Duplicate"))
            .andDo(print());
    }
}
