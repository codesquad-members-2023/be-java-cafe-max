package kr.codesqaud.cafe.post.repository;

import kr.codesqaud.cafe.post.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);
    Optional<Post> findByIndex(long index);
    List<Post> findAll();

}
