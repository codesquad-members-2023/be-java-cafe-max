package kr.codesqaud.cafe.post.repository;

import kr.codesqaud.cafe.post.service.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Post update(long id, Post post);

    void delete(long id);

    Optional<Post> findById(long id);

    List<Post> findAll();
}
