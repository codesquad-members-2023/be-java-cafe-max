package kr.codesqaud.cafe.repository.post;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
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
        return List.copyOf(store.values());
    }

    @Override
    public void update(Post post) {
        store.put(post.getId(), post);
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public void deleteAll() {
        store.clear();
    }
}
