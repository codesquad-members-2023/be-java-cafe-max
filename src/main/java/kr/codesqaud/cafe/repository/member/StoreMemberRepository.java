package kr.codesqaud.cafe.repository.member;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import kr.codesqaud.cafe.domain.Member;

@Repository
public class StoreMemberRepository implements MemberRepository {
    private final Map<Long, Member> store;

    public StoreMemberRepository() {
        this.store = new ConcurrentHashMap<>();
    }

    @Override
    public Long save(Member member) {
        store.put(member.getMemberId(), member);
        return member.getMemberId();
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(store.get(memberId));
    }

    @Override
    public List<Member> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public void update(Member member) {
        store.put(member.getMemberId(), member);
    }


    @Override
    public void deleteById(Long memberId) {
        store.remove(memberId);
    }

    @Override
    public void deleteAll() {
        store.clear();
    }
}
