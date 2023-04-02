package kr.codesqaud.cafe.repository.post;

import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import kr.codesqaud.cafe.domain.Post;

@Repository
public class PostMemoryStorage implements PostRepository{
    private final Map<UUID, Post> postMap;
    private final UUID postId;

    public PostMemoryStorage() {
        this.postMap = new HashMap<>();
        this.postId = UUID.randomUUID();
    }

    @Override
    public UUID save(Post post) {
        postMap.put(postId,post.of(post.getPostId()));
        return postId;
    }

    @Override
    public Optional<Post> findById(UUID postId) {
        return Optional.ofNullable(postMap.get(postId));
    }

}
