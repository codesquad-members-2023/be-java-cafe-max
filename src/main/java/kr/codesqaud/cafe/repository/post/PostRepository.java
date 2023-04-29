package kr.codesqaud.cafe.repository.post;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;


public interface PostRepository {
    Long save(Post post, Member member);

    Optional<Post> findById(Long id);

    List<Post> findPostByWriterEmail(String writerEmail);

    List<Post> findAll();

    void update(Post post);

    void deleteAll();

    void deleteId(Long id);
}
