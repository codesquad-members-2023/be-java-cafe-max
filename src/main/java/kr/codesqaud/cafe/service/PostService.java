package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.dto.PostWriteRequest;
import kr.codesqaud.cafe.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void writePost(PostWriteRequest postWriteRequest) {
        postRepository.save(postWriteRequest);
    }
}
