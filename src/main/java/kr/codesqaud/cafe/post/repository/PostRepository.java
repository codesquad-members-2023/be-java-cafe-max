package kr.codesqaud.cafe.post.repository;

import kr.codesqaud.cafe.post.service.Post;
import kr.codesqaud.cafe.post.controller.request.PostWriteRequest;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(PostWriteRequest postWriteRequest);

    Optional<Post> findById(long id);

    List<Post> findAll();
}
