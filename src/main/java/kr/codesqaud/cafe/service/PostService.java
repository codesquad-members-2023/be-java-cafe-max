package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostModifyRequest;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import kr.codesqaud.cafe.exception.post.PostNotFoundException;
import kr.codesqaud.cafe.repository.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Long write(PostWriteRequest postWriteRequest) {
        return postRepository.save(postWriteRequest.toPost());
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new)
            .increaseViews();
        postRepository.increaseViews(post);
        return PostResponse.of(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findAll() {
        return postRepository.findAll()
            .stream()
            .map(PostResponse::of)
            .collect(Collectors.toUnmodifiableList());
    }

    public void modify(PostModifyRequest postModifyRequest, Long accountSessionId) {
        validateUnauthorized(postModifyRequest.getId(), accountSessionId);
        postRepository.update(postModifyRequest.toPost());
    }

    @Transactional(readOnly = true)
    public void validateUnauthorized(Long id, Long accountSessionId) {
        Post findPost = postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);

        if (!findPost.equalsWriterId(accountSessionId)) {
            throw new UnauthorizedException();
        }
    }

    public void delete(Long id, Long accountSessionId) {
        validateUnauthorized(id, accountSessionId);
        postRepository.delete(id);
    }
}
