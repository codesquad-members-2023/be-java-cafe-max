package kr.codesqaud.cafe.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.exception.member.DuplicateMemberEmailException;
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
        given(memberRepository.findByEmail(signUpRequest.getEmail()))
            .willReturn(Optional.empty());

        // when
        Long savedId = memberService.signUp(signUpRequest);

        // then
        assertEquals(1L, savedId);
    }

    @DisplayName("회원 저장시 이메일이 중복인 경우 실패")
    @Test
    void createFalse2() {
        // given
        SignUpRequest signUpRequest = createRequestDummy2();
        Member member = signUpRequest.toMember().createWithId(1L);
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));

        // when

        // then
        assertThrows(DuplicateMemberEmailException.class,
            () -> memberService.signUp(new SignUpRequest(signUpRequest.getEmail()
                , "Test1111", "test")));
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
        ProfileEditRequest memberUpdateRequest = new ProfileEditRequest(1L, "mandu@gmail.com"
            , "Test1234", "Mandu1234", "mandu");
        given(memberRepository.findById(any()))
            .willReturn(Optional.of(createRequestDummy().toMember().createWithId(1L)))
            .willReturn(Optional.of(memberUpdateRequest.toMember(LocalDateTime.now())));
        given(memberRepository.findByEmail(any())).willReturn(Optional.empty());

        // when
        memberService.update(memberUpdateRequest);

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
        given(memberRepository.findById(any())).willReturn(Optional.empty());

        // when

        // then
        assertThrows(MemberNotFoundException.class,
            () -> memberService.update(new ProfileEditRequest(1L,
                "est@naver.com", "Test1234", "Test1234", "test")));
    }

    @DisplayName("회원 정보 수정시 이미 있는 회원의 이메일인 경우 실패")
    @Test
    void updateFalse2() {
        // given
        given(memberRepository.findById(any()))
            .willReturn(Optional.of(createRequestDummy().toMember().createWithId(1L)));
        given(memberRepository.findByEmail(createRequestDummy2().getEmail()))
            .willReturn(Optional.of(createRequestDummy2().toMember().createWithId(2L)));
        ProfileEditRequest memberUpdateRequest = new ProfileEditRequest(1L,
            createRequestDummy2().getEmail(), "Mandu1234", "Mandu7777", "updateMandu");

        // when

        // then
        assertThrows(DuplicateMemberEmailException.class,
            () -> memberService.update(memberUpdateRequest));
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
