package kr.codesqaud.cafe.post.repository;

import kr.codesqaud.cafe.post.service.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryPostRepository implements PostRepository {

    private final Map<Long, Post> posts = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Post save(Post post) {
        posts.put(++sequence, post.createPost(sequence));
        return post;
    }

    @Override
    public Optional<Post> findById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public List<Post> findAll() {
        return posts.values().stream().collect(Collectors.toUnmodifiableList());
    }
}
