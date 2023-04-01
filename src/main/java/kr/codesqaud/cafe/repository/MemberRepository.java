package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Member;

public interface MemberRepository {
    String save(Member member);
    Optional<Member> findById(String id);
    List<Member> findAll();
    void update(Member member);
}
