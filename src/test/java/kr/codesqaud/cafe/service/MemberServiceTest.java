package kr.codesqaud.cafe.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.IntStream;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberJoinRequestDto;
import kr.codesqaud.cafe.dto.member.MemberResponseDto;
import kr.codesqaud.cafe.dto.member.ProfileEditRequestDto;
import kr.codesqaud.cafe.repository.member.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:schema.sql")
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
    void join() {
        MemberJoinRequestDto memberLoginRequestDto = basicMemberData();

        Long savedMemberId = memberService.join(memberLoginRequestDto);

        //then
        Member targetMember = memberRepository.findById(savedMemberId).orElseThrow();
        assertAll(() -> assertEquals(savedMemberId, targetMember.getMemberId()),
                () -> assertEquals(memberLoginRequestDto.getEmail(), targetMember.getEmail()),
                () -> assertEquals(memberLoginRequestDto.getPassword(), targetMember.getPassword()),
                () -> assertEquals(memberLoginRequestDto.getNickName(), targetMember.getNickName()));
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
                    memberService.join(new MemberJoinRequestDto(email, password, nickName));
                });

        //when
        List<MemberResponseDto> members = memberService.findAll();

        //then
        assertEquals(memberNumber, members.size());
    }

    @Test
    @DisplayName("회원 단건 조회")
    void findById() {
        MemberJoinRequestDto requestDtoMember = basicMemberData();
        Long memberId = memberService.join(requestDtoMember);

        //when
        MemberResponseDto memberResponseDto = memberService.findById(memberId);

        //then
        assertAll(
                () -> assertEquals(memberId, memberResponseDto.getMemberId()),
                () -> assertEquals(requestDtoMember.getEmail(), memberResponseDto.getEmail()),
                () -> assertEquals(requestDtoMember.getNickName(), memberResponseDto.getNickName()));
    }

    @Test
    @DisplayName("회원 이메일 조회")
    void findByEmail() {
        MemberJoinRequestDto requestDtoMember = basicMemberData();
        Long memberId = memberService.join(requestDtoMember);
        String memberEmail = memberService.findById(memberId).getEmail();

        //when
        Member member = memberService.findByEmail(memberEmail);

        //then
        assertAll(
                () -> assertEquals(memberEmail, member.getEmail()),
                () -> assertEquals(requestDtoMember.getNickName(), member.getNickName()),
                () -> assertEquals(requestDtoMember.getPassword(), member.getPassword()));
    }


    @Test
    void update() {
        //given
        Long saveId = memberService.join(basicMemberData());
        ProfileEditRequestDto profileEditRequestDto = new ProfileEditRequestDto(saveId, dummyMemberData().getEmail(), dummyMemberData().getPassword(), dummyMemberData().getNickName());

        //when
        memberService.update(profileEditRequestDto);

        //then
        Member targetMember = memberRepository.findById(saveId).orElseThrow();
        assertAll(
                () -> assertEquals(saveId, targetMember.getMemberId()),
                () -> assertEquals(dummyMemberData().getNickName(), targetMember.getNickName()));
    }

    @Test
    void deleteById() {
        //given
        MemberJoinRequestDto memberLoginRequestDto = basicMemberData();
        Long userId = memberService.join(memberLoginRequestDto);

        //when
        memberService.deleteById(userId);

        //then
        List<MemberResponseDto> members = memberService.findAll();
        assertEquals(members.size(), 0);
    }

    private MemberJoinRequestDto basicMemberData() {
        String email = "test@gmail.com";
        String password = "testtest";
        String nickName = "차차";
        return new MemberJoinRequestDto(email, password, nickName);
    }

    private MemberJoinRequestDto dummyMemberData() {
        String email = "dummy@gmail.com";
        String password = "dummydummy";
        String nickName = "피오니";
        return new MemberJoinRequestDto(email, password, nickName);
    }
}