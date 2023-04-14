package kr.codesqaud.cafe.repository.member;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.annotation.RepositoryTest;
import kr.codesqaud.cafe.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository.deleteAll();
    }

    @DisplayName("회원 저장 성공")
    @Test
    void save() {
        // given

        // when
        Long savedId = memberRepository.save(dummyData());

        // then
        Optional<Member> findMember = memberRepository.findById(savedId);
        assertAll(
            () -> assertTrue(findMember.isPresent()),
            () -> assertEquals(savedId, findMember.get().getId()));
    }

    @DisplayName("아이디로 회원을 조회")
    @Test
    void findByID() {
        // given
        Member member = dummyData();
        Long savedId = memberRepository.save(member);

        // when
        Member findMember = memberRepository.findById(savedId).orElseThrow();

        // then
        assertAll(
            () -> assertEquals(member.getEmail(), findMember.getEmail()),
            () -> assertEquals(member.getPassword(), findMember.getPassword()),
            () -> assertEquals(member.getNickName(), findMember.getNickName()),
            () -> assertEquals(member.getCreateDate(), findMember.getCreateDate()));
    }

    @DisplayName("이메일로 멤버 조회")
    @Test
    void findByEmail() {
        // given
        Member member = dummyData();
        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findByEmail(member.getEmail()).orElseThrow();

        // then
        assertAll(
            () -> assertEquals(member.getEmail(), findMember.getEmail()),
            () -> assertEquals(member.getPassword(), findMember.getPassword()),
            () -> assertEquals(member.getNickName(), findMember.getNickName()),
            () -> assertEquals(member.getCreateDate(), findMember.getCreateDate()));
    }

    @DisplayName("모든 회원 조회")
    @Test
    void findAll() {
        // given
        Member member1 = dummyData();
        Member member2 = dummyData2();
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> findAll = memberRepository.findAll();

        // then
        assertAll(
            () -> assertEquals(2, findAll.size()),
            () -> assertEquals(member1.getEmail(), findAll.get(0).getEmail()),
            () -> assertEquals(member2.getEmail(), findAll.get(1).getEmail()));
    }

    @DisplayName("회원 정보 수정")
    @Test
    void update() {
        // given
        Member member = dummyData();
        Long savedId = memberRepository.save(member);
        Member updateMember = Member.builder()
            .id(savedId)
            .email("mandu@gmail.com")
            .password("mandu12345")
            .nickName("manduUpdat")
            .createDate(LocalDateTime.now())
            .build();

        // when
        memberRepository.update(updateMember);

        // then
        Member findMember = memberRepository.findById(savedId).orElseThrow();
        assertAll(
            () -> assertEquals(updateMember.getId(), findMember.getId()),
            () -> assertEquals(updateMember.getPassword(), findMember.getPassword()),
            () -> assertEquals(updateMember.getPassword(), findMember.getPassword()),
            () -> assertEquals(updateMember.getNickName(), findMember.getNickName()));
    }

    private Member dummyData() {
        return Member.builder()
            .email("mandu@gmail.com")
            .password("Mandu1234")
            .nickName("mandu")
            .createDate(LocalDateTime.now())
            .build();
    }

    private Member dummyData2() {
        return Member.builder()
            .email("test@gmail.com")
            .password("Test1234")
            .nickName("test")
            .createDate(LocalDateTime.now())
            .build();
    }
}
