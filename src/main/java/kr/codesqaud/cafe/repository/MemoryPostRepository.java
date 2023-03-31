package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.PostWriteRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public List<Post> findAll() {
        return posts.values().stream().collect(Collectors.toUnmodifiableList());
    }
}
