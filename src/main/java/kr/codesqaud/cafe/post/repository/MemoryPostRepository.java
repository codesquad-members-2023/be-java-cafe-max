package kr.codesqaud.cafe.post.repository;

import kr.codesqaud.cafe.post.service.Post;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class MemoryPostRepository implements PostRepository {

    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Post save(Post post) {
        posts.put(sequence.incrementAndGet(), post.create(sequence.get()));
        return post;
    }

    @Override
    public Post update(long id, Post post) {
        posts.put(id, post.create(id));
        return post;
    }

    @Override
    public void delete(long id) {
        posts.remove(id);
    }

    @Override
    public Optional<Post> findById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public List<Post> findAll() {
        return posts.values().stream()
                .sorted(Comparator.comparing(Post::getWritingTime).reversed())
                .collect(Collectors.toUnmodifiableList());
    }
}
