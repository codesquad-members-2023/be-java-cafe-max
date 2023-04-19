package kr.codesqaud.cafe.repository.member;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.annotation.RepositoryTest;
import kr.codesqaud.cafe.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이메일, 패스워드, 닉네임, 생성날짜를 입력할 때 저장하면 회원 아이디를 반환한다")
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

    @DisplayName("아이디를 입력시 해당 회원이 있을 때 회원을 조회하면 회원을 반환한다.")
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
            () -> assertEquals(member.getNickname(), findMember.getNickname()),
            () -> assertEquals(member.getCreateDate(), findMember.getCreateDate()));
    }

    @DisplayName("이메일를 입력시 해당 회원이 있을 때 회원을 조회하면 회원을 반환한다.")
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
            () -> assertEquals(member.getNickname(), findMember.getNickname()),
            () -> assertEquals(member.getCreateDate(), findMember.getCreateDate()));
    }

    @DisplayName("회원이 있을 때 회원 전체조회를 하면 모든 회원을 반환한다")
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

    @DisplayName("아이디, 이메일, 패스워드, 닉네임 입력시 해당 회원이 있을 때 회원 정보를 수정하면 데이터가 수정된다")
    @Test
    void update() {
        // given
        Member member = dummyData();
        Long savedId = memberRepository.save(member);
        Member updateMember = Member.builder()
            .id(savedId)
            .email("mandu@gmail.com")
            .password("mandu12345")
            .nickname("manduUpdat")
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
            () -> assertEquals(updateMember.getNickname(), findMember.getNickname()));
    }

    private Member dummyData() {
        return Member.builder()
            .email("mandu2@gmail.com")
            .password("Mandu1234")
            .nickname("mandu2")
            .createDate(LocalDateTime.now())
            .build();
    }

    private Member dummyData2() {
        return Member.builder()
            .email("test2@gmail.com")
            .password("Test1234")
            .nickname("test2")
            .createDate(LocalDateTime.now())
            .build();
    }
}
