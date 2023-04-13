package kr.codesqaud.cafe.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostModifyRequest;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.dto.post.WriterResponse;
import kr.codesqaud.cafe.exception.common.Unauthorized;
import kr.codesqaud.cafe.exception.member.MemberNotFoundException;
import kr.codesqaud.cafe.exception.post.PostNotFoundException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;
import kr.codesqaud.cafe.session.AccountSession;
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
        return postRepository.save(postWriteRequest.toPost());
    }

    @Transactional(readOnly = true)
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new)
            .increaseViews();
        postRepository.update(post);
        return PostResponse.of(post, getWhiterResponse(post));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findAll() {
        return postRepository.findAll()
            .stream()
            .sorted(Comparator.comparing(Post::getId)
                .reversed())
            .map(post -> PostResponse.of(post, getWhiterResponse(post)))
            .collect(Collectors.toUnmodifiableList());
    }

    private WriterResponse getWhiterResponse(Post post) {
        return Optional.ofNullable(post.getWriterId())
            .flatMap(memberRepository::findById)
            .map(WriterResponse::from)
            .orElseThrow(MemberNotFoundException::new);
    }

    public void modify(PostModifyRequest postModifyRequest) {
        postRepository.findById(postModifyRequest.getId())
                .orElseThrow(PostNotFoundException::new);
        postRepository.update(postModifyRequest.toPost());
    }

    public void validateUnauthorized(Long id, AccountSession accountSession) {
        Post findPost = postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);

        if (!findPost.equalsWriterId(accountSession.getId())) {
            throw new Unauthorized();
        }
    }
}
