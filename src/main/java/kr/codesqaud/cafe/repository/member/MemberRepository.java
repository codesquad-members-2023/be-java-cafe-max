package kr.codesqaud.cafe.repository.member;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Member;

public interface MemberRepository {
    Long save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    List<Member> findAll();

    void update(Member member);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
