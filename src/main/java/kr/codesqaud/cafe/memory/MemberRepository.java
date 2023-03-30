package kr.codesqaud.cafe.memory;

import kr.codesqaud.cafe.domain.Member;

public interface MemberRepository {
    Member save(Member member);
}
