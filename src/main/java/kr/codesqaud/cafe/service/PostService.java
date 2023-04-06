package kr.codesqaud.cafe.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.dto.post.WhiterResponse;
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

    public Long save(PostWriteRequest postWriteRequest) {
        return postRepository.save(postWriteRequest.toEntity());
    }

    @Transactional(readOnly = true)
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);
        post.increaseViews();
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

    private WhiterResponse getWhiterResponse(Post post) {
        WhiterResponse whiterResponse = null;

        if (post.getWriterId() != null) {
            whiterResponse = WhiterResponse.from(memberRepository.findById(post.getWriterId())
                .orElseThrow(MemberNotFoundException::new));
        }

        return whiterResponse;
    }
}
