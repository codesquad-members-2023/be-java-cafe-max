package kr.codesqaud.cafe.repository.member;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Member;

public interface MemberRepository {
    Long save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    List<Member> findAll();

    Optional<Member> findByEmailAndPassword(String email, String password);

    void update(Member member);

    boolean existByEmail(String email);

    boolean existByEmailAndIdNot(String email, Long id);
}
