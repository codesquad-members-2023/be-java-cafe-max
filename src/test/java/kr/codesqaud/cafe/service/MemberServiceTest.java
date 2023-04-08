package kr.codesqaud.cafe.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberResponseDto;
import kr.codesqaud.cafe.dto.member.ProfileEditRequestDto;
import kr.codesqaud.cafe.dto.member.SignUpRequestDto;
import kr.codesqaud.cafe.repository.member.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }


    @Test
    @DisplayName("회원 저장 성공")
    void signUp() {
        SignUpRequestDto signUpRequestDto = basicMemberData();

        String savedMemberId = memberService.signUp(signUpRequestDto);

        //then
        Member targetMember = memberRepository.findById(savedMemberId).orElseThrow();
        assertAll(() -> assertEquals(savedMemberId, targetMember.getMemberId()),
                () -> assertEquals(signUpRequestDto.getEmail(), targetMember.getEmail()),
                () -> assertEquals(signUpRequestDto.getPassword(), targetMember.getPassword()),
                () -> assertEquals(signUpRequestDto.getNickName(), targetMember.getNickName()));
    }


    @Test
    @DisplayName("모든 회원 조회 성공")
    void findAll() {
        //given
        int memberNumber = 10;
        IntStream.rangeClosed(1, memberNumber)
                .forEach(count -> {
                    String email = String.format("test%d@test.com", count);
                    String password = String.format("test%d", count);
                    String nickName = String.format("chacha%d", count);
                    memberService.signUp(new SignUpRequestDto(email, password, nickName));
                });

        //when
        List<MemberResponseDto> members = memberService.findAll();

        //then
        assertEquals(memberNumber, members.size());
    }

    @Test
    @DisplayName("회원 단건 조회")
    void findById() {
        SignUpRequestDto requestDtoMember1 = basicMemberData();
        String member1Id = memberService.signUp(requestDtoMember1);

        //when
        MemberResponseDto memberResponseDto = memberService.findById(member1Id);

        //then
        assertAll(
                () -> assertEquals(member1Id, memberResponseDto.getMemberId()),
                () -> assertEquals(requestDtoMember1.getEmail(), memberResponseDto.getEmail()),
                () -> assertEquals(requestDtoMember1.getNickName(), memberResponseDto.getNickName()));

    }

    @Test
    void update() {
        //given
        String saveId = memberService.signUp(basicMemberData());
        ProfileEditRequestDto profileEditRequestDto = new ProfileEditRequestDto(saveId, dummyMemberData().getEmail(), dummyMemberData().getPassword(), dummyMemberData().getNickName());

        //when
        memberService.update(profileEditRequestDto);

        //then
        Member targetMember = memberRepository.findById(saveId).orElseThrow();
        assertAll(
                () -> assertEquals(saveId, targetMember.getMemberId()),
                () -> assertEquals(dummyMemberData().getEmail(), targetMember.getEmail()),
                () -> assertEquals(dummyMemberData().getNickName(), targetMember.getNickName()));
    }

    @Test
    void deleteById() {
        //given
        SignUpRequestDto signUpRequestDto = basicMemberData();
        String userId = memberService.signUp(signUpRequestDto);

        //when
        memberService.deleteById(userId);

        //then
        List<MemberResponseDto> members = memberService.findAll();
        assertEquals(members.size(), 0);
    }

    private SignUpRequestDto basicMemberData() {
        String email = "test@test.com";
        String password = "testtest";
        String nickName = "chacha";
        return new SignUpRequestDto(email, password, nickName);
    }

    private SignUpRequestDto dummyMemberData() {
        String email = "dummy@dummy.com";
        String password = "dummydummy";
        String nickName = "피오니";
        return new SignUpRequestDto(email, password, nickName);
    }
}