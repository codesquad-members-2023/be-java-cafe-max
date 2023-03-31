package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.exception.member.MemberNotFoundException;
import kr.codesqaud.cafe.exception.post.PostNotFoundException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        return PostResponse.of(post, getMemberResponse(post));
    }

    public List<PostResponse> findAll() {
        return postRepository.findAll()
            .stream()
            .map(post -> PostResponse.of(post, getMemberResponse(post)))
            .collect(Collectors.toUnmodifiableList());
    }

    private MemberResponse getMemberResponse(Post post) {
        MemberResponse memberResponse = null;

        if (post.getWriterId() != null) {
            memberResponse = MemberResponse.of(memberRepository.findById(post.getWriterId())
                .orElseThrow(() -> new MemberNotFoundException(post.getWriterId())));
        }
        return memberResponse;
    }

    public Long save(PostWriteRequest postWriteRequest) {
        return postRepository.save(postWriteRequest.toEntity());
    }
}
