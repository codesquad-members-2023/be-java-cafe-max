package kr.codesqaud.cafe.repository.post;

import java.util.Comparator;
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
        store.put(id.get(), post.createWithId(id.get()));
        return id.getAndIncrement();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Post> findAll() {
        return store.values()
            .stream()
            .sorted(Comparator.comparing(Post::getWriteDate).reversed())
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteAll() {
        store.clear();
    }
}
