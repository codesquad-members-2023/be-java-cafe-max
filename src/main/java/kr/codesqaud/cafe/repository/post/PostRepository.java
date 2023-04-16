package kr.codesqaud.cafe.repository.post;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;


public interface PostRepository {
    Long save(Post post, Member member);

    Optional<Post> findById(Long postId);

    List<Post> findPostByWriterId(Long writerId);

    List<Post> findAll();

    void update(Post post);

    void deleteAll();
}
