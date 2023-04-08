package kr.codesqaud.cafe.repository.post;

import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;


@Repository
public class StorePostRepository implements PostRepository {
    private final Map<Long, Post> store;
    private final AtomicLong postIdGenerator = new AtomicLong(1);

    public StorePostRepository() {
        this.store = new ConcurrentHashMap<>();
    }

    @Override
    public Long save(Post post, Member writer) { //질문사항
        Long postId = postIdGenerator.getAndIncrement();
        post.setPostId(postId);
        store.put(postId, post.buildPost(writer));
        return postId;
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return Optional.ofNullable(store.get(postId));
    }

    @Override
    public List<Post> findPostIdByWriterId(String writerId) {
        return store.values().stream()
                .filter(post -> post.getWriterId().equals(writerId))
                .sorted(Comparator.comparing(Post::getWriteDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public void update(Post post) {
        store.put(post.getPostId(), post);
    }

    @Override
    public void deleteAll() {
        store.clear();
    }
}
