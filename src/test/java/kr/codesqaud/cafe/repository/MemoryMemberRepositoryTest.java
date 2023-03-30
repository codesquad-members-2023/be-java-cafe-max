package kr.codesqaud.cafe.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import kr.codesqaud.cafe.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemoryMemberRepositoryTest {

    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository = new MemoryMemberRepository();
    }

    @DisplayName("회원 저장 성공")
    @Test
    void save() {
        // given
        Member member = dummyData();

        // when
        String savedId = memberRepository.save(member);

        // then
        assertEquals(member.getId(), savedId);
    }

    @DisplayName("아이디로 회원을 조회")
    @Test
    void findByID() {
        // given
        Member member = dummyData();
        String savedId = memberRepository.save(member);

        // when
        Optional<Member> findMember = memberRepository.findById(savedId);

        // then
        assertAll(
            () -> assertTrue(findMember.isPresent()),
            () -> assertEquals(member, findMember.orElseThrow()));
    }

    @DisplayName("이메일로 멤버 조회")
    @Test
    void findByEmail() {
        // given
        Member member = dummyData();
        memberRepository.save(member);

        // when
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());

        // then
        assertAll(
            () -> assertTrue(findMember.isPresent()),
            () -> assertEquals(member, findMember.orElseThrow()));
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
            () -> assertEquals(member2, findAll.get(0)),
            () -> assertEquals(member1, findAll.get(1)));
    }

    @DisplayName("회원 정보 수정")
    @Test
    void update() {
        // given
        Member member = dummyData();
        memberRepository.save(member);
        Member updateMember = new Member(member.getId(), "mandu@gmail.com"
            , "mandu12345", "manduUpdate", LocalDateTime.now());

        // when
        memberRepository.update(updateMember);

        // then
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertAll(
            () -> assertTrue(findMember.isPresent()),
            () -> assertEquals(updateMember.getPassword(), findMember.get().getPassword()),
            () -> assertEquals(updateMember.getNickName(), findMember.get().getNickName()));
    }

    @DisplayName("회원 삭제")
    @Test
    void delete() {
        // given
        Member member = dummyData();
        String savedId = memberRepository.save(member);

        // when
        memberRepository.delete(savedId);

        // then
        assertThrows(NoSuchElementException.class, () -> memberRepository.findById(savedId).orElseThrow());
    }

    public Member dummyData() {
        return new Member(UUID.randomUUID().toString(), "mandu@gmail.com"
            , "mandu1234", "mandu", LocalDateTime.now());
    }

    public Member dummyData2() {
        return new Member(UUID.randomUUID().toString(), "test@gmail.com"
            , "test1234", "test", LocalDateTime.now());
    }
}
