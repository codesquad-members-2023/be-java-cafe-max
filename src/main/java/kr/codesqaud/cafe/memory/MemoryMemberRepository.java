package kr.codesqaud.cafe.memory;

import kr.codesqaud.cafe.domain.Member;

import java.util.List;
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

    @Override
    public Member findMemberByUid(Long uid) {
        return repository.get(uid);
    }

    @Override
    public Member findMemberByLoginId(String loginId) {
        return null;
    }

    @Override
    public Member findMemberByEmail(String email) {
        return null;
    }

    @Override
    public List<Member> findAllMember() {
        return null;
    }
}
