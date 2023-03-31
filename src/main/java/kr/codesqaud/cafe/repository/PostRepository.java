package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.PostWriteRequest;

import java.util.List;

public interface PostRepository {

    Post save(PostWriteRequest postWriteRequest);

    List<Post> findAll();
}
