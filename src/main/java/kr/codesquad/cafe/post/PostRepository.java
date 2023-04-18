package kr.codesquad.cafe.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndAndIsDeleted(long id, boolean deleted);

    List<Post> findAllByIsDeleted(boolean deleted);
}
