package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Member;
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
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Long write(PostWriteRequest postWriteRequest) {
        return postRepository.save(postWriteRequest.toPost(Member.builder()
            .id(postWriteRequest.getWriterId())
            .build()));
    }

    @Transactional
    public PostResponse findById(Long id) {
        postRepository.increaseViews(id);
        return PostResponse.from(postRepository.findById(id).orElseThrow(PostNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findAll() {
        return postRepository.findAll()
            .stream()
            .map(PostResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
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

    @Transactional
    public void delete(Long id, Long accountSessionId) {
        validateUnauthorized(id, accountSessionId);
        postRepository.delete(id);
    }
}
