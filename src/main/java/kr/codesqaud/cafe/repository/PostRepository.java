package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);
    Optional<Post> findById(long id);
    List<Post> findAll();
}
