package kr.codesqaud.cafe.repository;

import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import kr.codesqaud.cafe.domain.Member;

@Repository
public class MemoryMemberRepository implements MemberRepository {
    private final Map<String, Member> store;

    public MemoryMemberRepository() {
        this.store = new ConcurrentHashMap<>();
    }

    @Override
    public String save(Member member) {
        store.put(member.getId(), member);
        return member.getId();
    }



    @Override
    public List<Member> findAll() {
        return store.values()
                .stream()
                .sorted(Comparator.comparing(Member::getCreateTime).reversed())
                .collect(Collectors.toUnmodifiableList());
    }

}
