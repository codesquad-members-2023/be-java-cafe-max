package kr.codesqaud.cafe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import kr.codesqaud.cafe.controller.MemberController;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.MemberResponseDto;
import kr.codesqaud.cafe.dto.ProfileEditRequestDto;
import kr.codesqaud.cafe.dto.SignUpRequestDto;
import kr.codesqaud.cafe.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void beforeEach() {
        if (memberRepository != null) {
            memberRepository.deleteAll();
        }
    }


    @Test
    @DisplayName("회원 저장 성공")
    void signUp() {
        SignUpRequestDto signUpRequestDto = basicMemberData();

        String savedMemberId = memberService.signUp(signUpRequestDto);

        //then
        Member targetMember = memberRepository.findById(savedMemberId).orElseThrow();
        assertAll(() -> assertEquals(savedMemberId, targetMember.getId()),
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
                    LocalDateTime localDateTime = LocalDateTime.now();
                    memberService.signUp(new SignUpRequestDto(email, password, nickName, localDateTime));
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
                () -> assertEquals(member1Id, memberResponseDto.getId()),
                () -> assertEquals(requestDtoMember1.getEmail(), memberResponseDto.getEmail()),
                () -> assertEquals(requestDtoMember1.getNickName(), memberResponseDto.getNickName()),
                () -> assertEquals(requestDtoMember1.getCreateDate(), memberResponseDto.getCreateTime()));

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
                () -> assertEquals(saveId, targetMember.getId()),
                () -> assertEquals(dummyMemberData().getEmail(), targetMember.getEmail()),
                () -> assertEquals(dummyMemberData().getNickName(), targetMember.getNickName()));
    }

    private SignUpRequestDto basicMemberData() {
        String email = "test@test.com";
        String password = "testtest";
        String nickName = "chacha";
        LocalDateTime localDateTime = LocalDateTime.now();
        return new SignUpRequestDto(email, password, nickName, localDateTime);
    }

    private SignUpRequestDto dummyMemberData() {
        String email = "dummy@dummy.com";
        String password = "dummydummy";
        String nickName = "피오니";
        LocalDateTime localDateTime = LocalDateTime.now();
        return new SignUpRequestDto(email, password, nickName, localDateTime);
    }
}