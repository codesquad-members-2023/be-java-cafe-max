package kr.codesqaud.cafe.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.exception.member.DuplicateMemberEmailException;
import kr.codesqaud.cafe.exception.member.MemberNotFoundException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository.deleteAll();
    }

    @DisplayName("회원 저장 성공")
    @Test
    void create() {
        // given
        SignUpRequest signUpRequest = createRequestDummy();

        // when
        String savedId = memberService.signUp(signUpRequest);

        // then
        Member findMember = memberRepository.findById(savedId).orElseThrow();
        assertAll(
            () -> assertEquals(savedId, findMember.getId()),
            () -> assertEquals(signUpRequest.getEmail(), findMember.getEmail()),
            () -> assertEquals(signUpRequest.getPassword(), findMember.getPassword()),
            () -> assertEquals(signUpRequest.getNickName(), findMember.getNickName()));
    }

    @DisplayName("회원 저장시 이메일이 중복인 경우 실패")
    @Test
    void createFalse2() {
        // given
        SignUpRequest signUpRequest = createRequestDummy2();
        memberService.signUp(signUpRequest);

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
        SignUpRequest memberCreateRequest = createRequestDummy2();
        String savedId = memberService.signUp(memberCreateRequest);

        // when
        MemberResponse memberResponse = memberService.findById(savedId);

        // then
        assertAll(
            () -> assertEquals(savedId, memberResponse.getId()),
            () -> assertEquals(memberCreateRequest.getEmail(), memberResponse.getEmail()),
            () -> assertEquals(memberCreateRequest.getNickName(), memberResponse.getNickName()),
            () -> assertEquals(memberCreateRequest.getCreateDate(), memberResponse.getCreateDate()));
    }

    @DisplayName("회원 단건 조회 실패")
    @Test
    void findByIdFalse() {
        // given

        // then

        // when
        assertThrows(MemberNotFoundException.class,
            () -> memberService.findById(UUID.randomUUID().toString()));
    }

    @DisplayName("모든 회원 조회 성공")
    @Test
    void findAll() {
        // given
        int memberCount = 10;
        IntStream.rangeClosed(1, memberCount)
            .forEach(index -> {
                String email = String.format("test%d@gmail.com", index);
                String password = String.format("Test123%d", index);
                String nickName = String.format("mandu%d", index);
                memberService.signUp(new SignUpRequest(email, password, nickName));
            });

        // when
        List<MemberResponse> findAll = memberService.findAll();

        // then
        assertEquals(memberCount, findAll.size());
    }

    @DisplayName("회원 정보 수정 성공")
    @Test
    void update() {
        // given
        String savedId = memberService.signUp(createRequestDummy());

        String updateEmail = "mandu@gmail.com";
        String updatePassword = "Mandu1234";
        String updateNickName = "mandu";
        ProfileEditRequest memberUpdateRequest = new ProfileEditRequest(savedId, updateEmail
            , updatePassword, updateNickName);

        // when
        memberService.update(memberUpdateRequest);

        // then
        Member findMember = memberRepository.findById(savedId).orElseThrow();
        assertAll(
            () -> assertEquals(savedId, findMember.getId()),
            () -> assertEquals(updateEmail, findMember.getEmail()),
            () -> assertEquals(updatePassword, findMember.getPassword()),
            () -> assertEquals(updateNickName, findMember.getNickName()));
    }

    @DisplayName("회원 정보 수정시 수정할 멤버가 없는 경우 실패")
    @Test
    void updateFalse() {
        // given

        // when

        // then
        assertThrows(MemberNotFoundException.class,
            () -> memberService.update(new ProfileEditRequest(UUID.randomUUID().toString()
                , "est@naver.com", "Test1234", "test")));
    }

    @DisplayName("회원 정보 수정시 이미 있는 회원의 이메일인 경우 실패")
    @Test
    void updateFalse2() {
        // given
        SignUpRequest memberCreateRequest = createRequestDummy();
        memberService.signUp(memberCreateRequest);
        String savedId2 = memberService.signUp(createRequestDummy2());
        ProfileEditRequest memberUpdateRequest = new ProfileEditRequest(savedId2,
            memberCreateRequest.getEmail(),
            "Mandu7777", "updateMandu");

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
