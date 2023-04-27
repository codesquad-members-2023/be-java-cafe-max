package kr.codesqaud.cafe.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.authentication.SignInRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import kr.codesqaud.cafe.exception.member.MemberDuplicateEmailException;
import kr.codesqaud.cafe.exception.member.MemberInvalidPassword;
import kr.codesqaud.cafe.exception.member.MemberNotFoundException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @DisplayName("이메일, 패스워드, 닉네임 입력할 때 저장을 하면 회원 아이디를 반환한다")
    @Test
    void signUp() {
        // given
        SignUpRequest signUpRequest = createRequestDummy();
        given(memberRepository.save(any())).willReturn(1L);

        // when
        Long savedId = memberService.signUp(signUpRequest);

        // then
        assertEquals(1L, savedId);
    }

    @DisplayName("이메일, 패스워드, 닉네임 입력시 회원 중에 중복된 이메일이 있을 때 저장을 하면 에러를 반환한다")
    @Test
    void signUpFalse() {
        // given
        SignUpRequest signUpRequest = createRequestDummy();
        given(memberRepository.existsByEmail(any())).willReturn(true);

        // when

        // then
        assertThrows(MemberDuplicateEmailException.class, () -> memberService.signUp(signUpRequest));
    }

    @DisplayName("아이디를 입력시 해당 회원이 있을 때 조회하면 회원을 반환한다")
    @Test
    void findById() {
        // given
        Long savedId = 1L;
        Member member = Member.builder()
            .id(savedId)
            .email("mandu@gmail.com")
            .password("Mandu1234")
            .nickname("mandu")
            .createDate(LocalDateTime.now())
            .build();
        given(memberRepository.findById(any()))
            .willReturn(Optional.of(member));

        // when
        MemberResponse memberResponse = memberService.findById(savedId);

        // then
        assertAll(
            () -> assertEquals(1L, memberResponse.getId()),
            () -> assertEquals("mandu@gmail.com", memberResponse.getEmail()),
            () -> assertEquals("mandu", memberResponse.getNickname()));
    }

    @DisplayName("아이디를 입력시 해당 회원이 없을 때 조회하면 에러를 반환한다")
    @Test
    void findByIdFalse() {
        // given
        given(memberRepository.findById(any())).willThrow(MemberNotFoundException.class);

        // then

        // when
        assertThrows(MemberNotFoundException.class, () -> memberService.findById(1L));
    }

    @DisplayName("조회할 회원의 이메일이 같을 때 이메일로 회원을 조회하면 회원을 반환한다")
    @Test
    void findEmail() {
        // given
        Long savedId = 1L;
        String email = "test@gmail.com";
        Member member = Member.builder()
            .id(savedId)
            .email(email)
            .password("Test1234")
            .nickname("test")
            .createDate(LocalDateTime.now())
            .build();
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));

        // when
        MemberResponse findMemberResponse = memberService.findByEmail(email);

        // then
        Assertions.assertThat(findMemberResponse.getId()).isEqualTo(1L);
        assertEquals(1L, findMemberResponse.getId());
    }

    @DisplayName("조회할 회원의 이메일이 같지 않을 떄 이메일로 회원을 조회하면 예외를 던진다")
    @Test
    void findEmailFalse() {
        // given
        String email = "test@gmail.com";
        given(memberRepository.findByEmail(email)).willReturn(Optional.empty());

        // when

        // then
        assertThrows(MemberNotFoundException.class, () -> memberService.findByEmail(email));
    }

    @DisplayName("회원이 있을 때 회원들을 조회하면 모든 회원을 반환한다")
    @Test
    void findAll() {
        // given
        Member member = Member.builder()
            .id(1L)
            .email("test@naver.com")
            .password("Test1234")
            .nickname("test")
            .createDate(LocalDateTime.now())
            .build();
        Member member2 = Member.builder()
            .id(2L)
            .email("mandu@gmail.com")
            .password("Mandu1234")
            .nickname("mandu")
            .createDate(LocalDateTime.now())
            .build();
        given(memberRepository.findAll()).willReturn(List.of(member, member2));

        // when
        List<MemberResponse> findAll = memberService.findAll();

        // then
        assertEquals(2, findAll.size());
    }

    @DisplayName("아이디, 패스워드, 변경할 비밀번호, 닉네임, 세션 아이디 입력시 검증을 통과할 때 회원 정보를 수정히면 회원 정보가 수정된다")
    @Test
    void update() {
        // given
        Long memberId = 1L;
        AccountSession accountSession = new AccountSession(memberId, "만두");
        ProfileEditRequest memberUpdateRequest = new ProfileEditRequest(memberId, "mandu@gmail.com"
            , "Test1234", "Mandu1234", "mandu");
        Member member = Member.builder()
            .id(memberId)
            .email("mandu@gmail.com")
            .password("Test1234")
            .nickname("mandu")
            .createDate(LocalDateTime.now())
            .build();
        given(memberRepository.findById(any()))
            .willReturn(Optional.of(member))
            .willReturn(Optional.of(memberUpdateRequest.toMember()));

        // when
        memberService.update(memberUpdateRequest, accountSession.getId());

        // then
        Member findMember = memberRepository.findById(1L).orElseThrow();
        assertAll(
            () -> assertEquals(1L, findMember.getId()),
            () -> assertEquals("mandu@gmail.com", findMember.getEmail()),
            () -> assertEquals("Mandu1234", findMember.getPassword()),
            () -> assertEquals("mandu", findMember.getNickname()));
    }

    @DisplayName("아이디, 패스워드, 변경할 비밀번호, 닉네임, 세션 아이디 입력시 수정할 회원이 없을 때 회원 정보를 수정하면 에러를 반환한다")
    @Test
    void updateFalse() {
        // given
        Long memberId = 1L;
        AccountSession accountSession = new AccountSession(memberId, "만두");
        ProfileEditRequest profileEditRequest = new ProfileEditRequest(memberId, "est@naver.com",
            "Test1234", "Test1234", "test");
        given(memberRepository.findById(any())).willReturn(Optional.empty());

        // when

        // then
        assertThrows(MemberNotFoundException.class,
            () -> memberService.update(profileEditRequest, accountSession.getId()));
    }

    @DisplayName("아이디, 패스워드, 변경할 비밀번호, 닉네임, 세션 아이디 입력시 세션 아이디랑 아이디가 다를 때 회원 정보를 수정하면 에러를 반환한다")
    @Test
    void updateFalse2() {
        // given
        Long memberId = 1L;
        AccountSession accountSession = new AccountSession(2L, "만두");
        ProfileEditRequest profileEditRequest = new ProfileEditRequest(memberId, "est@naver.com",
            "Test1234", "Test1234", "test");
        given(memberRepository.findById(any())).willReturn(Optional.of(profileEditRequest.toMember()));

        // when

        // then
        assertThrows(UnauthorizedException.class,
            () -> memberService.update(profileEditRequest, accountSession.getId()));
    }

    @DisplayName("아이디, 패스워드, 변경할 비밀번호, 닉네임, 세션 아이디 입력시 회원 중에 중복된 이메일이 있을 때 회원 정보를 수정하면 에러를 반환한다")
    @Test
    void updateFalse3() {
        // given
        Long memberId = 1L;
        AccountSession accountSession = new AccountSession(1L, "만두");
        ProfileEditRequest profileEditRequest = new ProfileEditRequest(memberId, "est@naver.com",
            "Test1234", "Test1234", "test");
        given(memberRepository.findById(any())).willReturn(Optional.of(profileEditRequest.toMember()));
        given(memberRepository.existsByEmailAndIdNot(any(), any())).willReturn(true);

        // when

        // then
        assertThrows(MemberDuplicateEmailException.class,
            () -> memberService.update(profileEditRequest, accountSession.getId()));
    }

    @DisplayName("아이디, 패스워드, 변경할 비밀번호, 닉네임, 세션 아이디 입력시 기존 비밀번호 틀릴 때 회원 정보를 수정하면 에러를 반환한다")
    @Test
    void updateFalse4() {
        // given
        Long memberId = 1L;
        AccountSession accountSession = new AccountSession(1L, "만두");
        ProfileEditRequest profileEditRequest = new ProfileEditRequest(memberId, "est@naver.com",
            "Test1234", "Test1234", "test");
        Member member = Member.builder()
            .id(profileEditRequest.getId())
            .email(profileEditRequest.getEmail())
            .password("Test1222")
            .nickname(profileEditRequest.getNickname())
            .createDate(LocalDateTime.now())
            .build();
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        // when

        // then
        assertThrows(MemberInvalidPassword.class,
            () -> memberService.update(profileEditRequest, accountSession.getId()));
    }

    private SignUpRequest createRequestDummy() {
        String email = "test@naver.com";
        String password = "Test1234";
        String nickName = "test";
        return new SignUpRequest(email, password, nickName);
    }

    private SignUpRequest createRequestDummy2() {
        String email = "mandu@gmail.com";
        String password = "Mandu1234";
        String nickName = "mandu";
        return new SignUpRequest(email, password, nickName);
    }
}
