package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostModifyRequest;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import kr.codesqaud.cafe.exception.member.MemberNotFoundException;
import kr.codesqaud.cafe.exception.post.PostNotFoundException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;
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

    public Long write(PostWriteRequest postWriteRequest) {
        Member member = memberRepository.findById(postWriteRequest.getWriterId())
            .orElseThrow(MemberNotFoundException::new);
        return postRepository.save(postWriteRequest.toPost(member));
    }

    public PostResponse findById(Long id, Long accountSessionId) {
        validateUnauthorized(id, accountSessionId);
        postRepository.increaseViews(id);
        return PostResponse.of(postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new));
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
