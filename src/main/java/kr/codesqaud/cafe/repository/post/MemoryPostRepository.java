package kr.codesqaud.cafe.repository.post;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Post;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryPostRepository implements PostRepository {

    private final Map<Long, Post> store;
    private final AtomicLong id;

    public MemoryPostRepository() {
        this.store = new ConcurrentHashMap<>();
        this.id = new AtomicLong(1);
    }

    @Override
    public Long save(Post post) {
        store.put(id.get(), post.of(id.get()));
        return id.getAndIncrement();
    }

    @Override
    public Optional<Post> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Post> findAll() {
        return store.values()
            .stream()
            .sorted((o1, o2) -> o2.getWriteDate().compareTo(o1.getWriteDate()))
            .collect(Collectors.toUnmodifiableList());
    }
}
