package kr.codesqaud.cafe.repository.post;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Post;

public interface PostRepository {

    Long save(Post post);

    Optional<Post> findById(Long id);

    List<Post> findAll(Integer offset, Integer pageSize);

    void update(Post post);

    void increaseViews(Long id);

    void delete(Long id);

    int postsSize();
}
