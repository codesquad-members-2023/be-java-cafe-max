package kr.codesqaud.cafe.repository.member;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Member;

public interface MemberRepository {
    String save(Member member);

    Optional<Member> findById(Long memberId);

    List<Member> findAll();

    void update(Member member);

    void deleteById(Long memberId);

    void deleteAll();
}
