package kr.codesqaud.cafe.post.repository;

import kr.codesqaud.cafe.post.service.Post;
import kr.codesqaud.cafe.post.controller.request.PostWriteRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryPostRepository implements PostRepository {

    private final Map<Long, Post> posts = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Post save(PostWriteRequest postWriteRequest) {
        Post post = postWriteRequest.toEntity(++sequence);
        posts.put(sequence, post);
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
