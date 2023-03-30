package kr.codesqaud.cafe.memory;

import kr.codesqaud.cafe.domain.Member;

import java.util.List;

public interface MemberRepository {
    Member save(Member member);
    Member findMemberByUid(Long id);

    Member findMemberByLoginId(String loginId);
    Member findMemberByEmail(String email);
    List<Member> findAllMember();
}
