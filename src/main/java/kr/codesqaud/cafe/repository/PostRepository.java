package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.PostWriteRequest;

public interface PostRepository {

    Post save(PostWriteRequest postWriteRequest);
}
