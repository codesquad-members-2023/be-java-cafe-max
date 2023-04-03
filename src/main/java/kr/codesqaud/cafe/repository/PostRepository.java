package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.PostWriteRequest;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(PostWriteRequest postWriteRequest);

    Optional<Post> findById(long id);

    List<Post> findAll();
}
