package kr.codesqaud.cafe.repository.member;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Member;

public interface MemberRepository {
    Long save(Member member);

    Optional<Member> findById(Long memberId);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickName(String nickName);

    List<Member> findAll();

    void update(Member member);

    void deleteById(Long memberId);

    void deleteAll();
}
