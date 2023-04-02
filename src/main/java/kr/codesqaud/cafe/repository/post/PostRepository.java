package kr.codesqaud.cafe.repository.post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import kr.codesqaud.cafe.domain.Post;

public interface PostRepository {
    UUID save(Post post);
    Optional<Post> findById(UUID postId);
    List<Post> findAll();
}
