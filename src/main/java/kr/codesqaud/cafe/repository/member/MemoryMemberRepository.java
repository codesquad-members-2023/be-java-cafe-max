package kr.codesqaud.cafe.repository.member;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private final Map<Long, Member> store;
    private final AtomicLong id;

    public MemoryMemberRepository() {
        this.store = new ConcurrentHashMap<>();
        this.id = new AtomicLong(1);
    }

    @Override
    public Long save(Member member) {
        store.put(id.get(), member.createWithId(id.get()));
        return id.getAndIncrement();
    }

    @Override
    public Optional<Member> findById(Long id) {
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
            .sorted(Comparator.comparing(Member::getId)
                .reversed())
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void update(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public void deleteAll() {
        store.clear();
    }
}
