package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostModifyRequest;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import kr.codesqaud.cafe.exception.post.PostNotFoundException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;
import kr.codesqaud.cafe.config.session.AccountSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public Long write(PostWriteRequest postWriteRequest, Long id) {
        postWriteRequest.setWriterId(id);
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

    public void modify(PostModifyRequest postModifyRequest, Long id) {
        postModifyRequest.setId(id);
        postRepository.findById(postModifyRequest.getId())
            .orElseThrow(PostNotFoundException::new);
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

    public void delete(Long id) {
        postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);
        postRepository.delete(id);
    }
}
