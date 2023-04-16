package kr.codesqaud.cafe.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.exception.member.MemberNotFoundException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
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

    @DisplayName("회원 저장 성공")
    @Test
    void create() {
        // given
        SignUpRequest signUpRequest = createRequestDummy();
        given(memberRepository.save(any())).willReturn(1L);

        // when
        Long savedId = memberService.signUp(signUpRequest);

        // then
        assertEquals(1L, savedId);
    }

    @DisplayName("회원 단건 조회 성공")
    @Test
    void findById() {
        // given
        Long savedId = 1L;
        SignUpRequest memberCreateRequest = createRequestDummy2();
        given(memberRepository.findById(any()))
            .willReturn(Optional.of(memberCreateRequest.toMember().createWithId(savedId)));

        // when
        MemberResponse memberResponse = memberService.findById(savedId);

        // then
        assertAll(
            () -> assertEquals(savedId, memberResponse.getId()),
            () -> assertEquals(memberCreateRequest.getEmail(), memberResponse.getEmail()),
            () -> assertEquals(memberCreateRequest.getNickName(), memberResponse.getNickName()),
            () -> assertEquals(memberCreateRequest.getCreateDate(),
                memberResponse.getCreateDate()));
    }

    @DisplayName("회원 단건 조회 실패")
    @Test
    void findByIdFalse() {
        // given
        given(memberRepository.findById(any())).willThrow(MemberNotFoundException.class);

        // then

        // when
        assertThrows(MemberNotFoundException.class,
            () -> memberService.findById(1L));
    }

    @DisplayName("모든 회원 조회 성공")
    @Test
    void findAll() {
        // given
        Member member = createRequestDummy().toMember().createWithId(1L);
        Member member2 = createRequestDummy2().toMember().createWithId(2L);
        given(memberRepository.findAll()).willReturn(List.of(member, member2));

        // when
        List<MemberResponse> findAll = memberService.findAll();

        // then
        assertEquals(2, findAll.size());
    }

    @DisplayName("회원 정보 수정 성공")
    @Test
    void update() {
        // given
        Long memberId = 1L;
        AccountSession accountSession = new AccountSession(memberId);
        ProfileEditRequest memberUpdateRequest = new ProfileEditRequest(memberId, "mandu@gmail.com"
            , "Test1234", "Mandu1234", "mandu");
        given(memberRepository.findById(any()))
            .willReturn(Optional.of(createRequestDummy().toMember().createWithId(memberId)))
            .willReturn(Optional.of(memberUpdateRequest.toMember()));

        // when
        memberService.update(memberUpdateRequest, accountSession);

        // then
        Member findMember = memberRepository.findById(1L).orElseThrow();
        assertAll(
            () -> assertEquals(memberUpdateRequest.getId(), findMember.getId()),
            () -> assertEquals(memberUpdateRequest.getEmail(), findMember.getEmail()),
            () -> assertEquals(memberUpdateRequest.getNewPassword(), findMember.getPassword()),
            () -> assertEquals(memberUpdateRequest.getNickName(), findMember.getNickName()));
    }

    @DisplayName("회원 정보 수정시 수정할 멤버가 없는 경우 실패")
    @Test
    void updateFalse() {
        // given
        Long memberId = 1L;
        AccountSession accountSession = new AccountSession(memberId);
        given(memberRepository.findById(any())).willReturn(Optional.empty());

        // when

        // then
        assertThrows(MemberNotFoundException.class,
            () -> memberService.update(new ProfileEditRequest(memberId, "est@naver.com",
                "Test1234", "Test1234", "test"), accountSession));
    }

    @DisplayName("회원 중에 중복 이메일 검사 시 중복이 있는 경우 성공")
    @Test
    void isDuplicateEmail() {
        // given
        String email = "test@gmail.com";
        Member member = Member.builder()
            .id(1L)
            .email(email)
            .password("Test1234")
            .nickName("test")
            .createDate(LocalDateTime.now())
            .build();
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));

        // when
        boolean actual = memberService.isDuplicateEmail(email);

        // then
        assertTrue(actual);
    }

    @DisplayName("회원 중에 중복 이메일 검사 시 중복이 없는 경우 실패")
    @Test
    void isDuplicateEmailFalse() {
        // given
        String email = "test@gmail.com";
        given(memberRepository.findByEmail(email)).willReturn(Optional.empty());

        // when
        boolean actual = memberService.isDuplicateEmail(email);

        // then
        assertFalse(actual);
    }

    @DisplayName("회원 중에 중복 이메일과 같은 아이디 검사 시 이메일 중복이 있고 중복인 이메일의 회원 아이디가 다를 경우 성공")
    @Test
    void isDuplicateEmailAndId() {
        // given
        Long id = 1L;
        String email = "test@gmail.com";
        Member member = Member.builder()
            .id(2L)
            .email(email)
            .password("Test1234")
            .nickName("test")
            .createDate(LocalDateTime.now())
            .build();
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));

        // when
        boolean actual = memberService.isDuplicateEmailAndId(email, id);

        // then
        assertTrue(actual);
    }

    @DisplayName("회원 중에 중복 이메일과 같은 아이디 검사 시 이메일 중복이 있고 중복인 이메일의 회원 아이디가 같을 경우 성공")
    @Test
    void isDuplicateEmailAndIdFalse() {
        // given
        Long id = 1L;
        String email = "test@gmail.com";
        Member member = Member.builder()
            .id(id)
            .email(email)
            .password("Test1234")
            .nickName("test")
            .createDate(LocalDateTime.now())
            .build();
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));

        // when
        boolean actual = memberService.isDuplicateEmailAndId(email, id);

        // then
        assertFalse(actual);
    }

    @DisplayName("회원 비밀번호 검사 시 같지 않을 경우 성공")
    @Test
    void isNotSamePassword() {
        // given
        String email = "test@gmail.com";
        String password = "Test1234";
        Member member = Member.builder()
            .id(1L)
            .email(email)
            .password("Test1233")
            .nickName("test")
            .createDate(LocalDateTime.now())
            .build();
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));

        // when
        boolean actual = memberService.isNotSamePassword(email, password);

        // then
        assertTrue(actual);
    }

    @DisplayName("회원 비밀번호 검사 시 같을 경우 실패")
    @Test
    void isNotSamePasswordFalse() {
        // given
        String email = "test@gmail.com";
        String password = "Test1234";
        Member member = Member.builder()
            .id(1L)
            .email(email)
            .password(password)
            .nickName("test")
            .createDate(LocalDateTime.now())
            .build();
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));

        // when
        boolean actual = memberService.isNotSamePassword(email, password);

        // then
        assertFalse(actual);
    }

    @DisplayName("이메일을 받아서 회원 중에 이메일이 같은 회원을 찾은 후 해당 회원 정보를 바탕으로 AccountSession을 반환한다")
    @Test
    void createSession() {
        // given
        Long savedId = 1L;
        String email = "test@gmail.com";
        Member member = Member.builder()
            .id(savedId)
            .email(email)
            .password("Test1234")
            .nickName("test")
            .createDate(LocalDateTime.now())
            .build();
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));

        // when
        AccountSession session = memberService.createSession(email);

        // then
        assertEquals(savedId, session.getId());
    }

    @DisplayName("이메일을 받아서 회원 중에 이메일이 같은 회원을 못 찾은 경우 예외를 던진다")
    @Test
    void createSessionFalse() {
        // given
        String email = "test@gmail.com";
        given(memberRepository.findByEmail(email)).willReturn(Optional.empty());

        // when

        // then
        assertThrows(MemberNotFoundException.class, () -> memberService.createSession(email));
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
