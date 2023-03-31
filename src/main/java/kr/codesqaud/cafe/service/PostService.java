package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.repository.post.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostResponse> findAll() {
        return postRepository.findAll()
            .stream()
            .map(post -> PostResponse.of(post, null))
            .collect(Collectors.toUnmodifiableList());
    }
}
