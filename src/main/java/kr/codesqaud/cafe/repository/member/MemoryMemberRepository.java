package kr.codesqaud.cafe.repository.member;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Member;
import org.springframework.stereotype.Repository;

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
    public Optional<Member> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return store.values().stream()
            .filter(m -> m.equalsEmail(email))
            .findFirst();
    }

    @Override
    public List<Member> findAll() {
        return store.values()
            .stream()
            .sorted(Comparator.comparing(Member::getCreateDate).reversed())
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void update(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public void delete(String id) {
        store.remove(id);
    }

    @Override
    public void deleteAll() {
        store.clear();
    }
}
