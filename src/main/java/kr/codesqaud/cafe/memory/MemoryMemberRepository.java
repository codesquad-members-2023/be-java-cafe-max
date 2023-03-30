package kr.codesqaud.cafe.memory;

import kr.codesqaud.cafe.domain.Member;

import java.util.concurrent.ConcurrentHashMap;

public class MemoryMemberRepository implements MemberRepository {

    private static ConcurrentHashMap<Long, Member> repository = new ConcurrentHashMap<>();

    private static Long numberOfUsers = 0L;

    @Override
    public Member save(Member member) {
        member.setUid(++numberOfUsers);
        repository.put(member.getUid(), member);
        return member;
    }
}
