package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.dto.post.WriterResponse;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public Long save(PostWriteRequest postWriteRequest) {
        Member member = memberRepository.findById(postWriteRequest.getWriterId()).orElseThrow();
        return postRepository.save(postWriteRequest.toMakePost(member), member);
    }

    public PostResponse findById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("해당 id를 가진 글을 찾을 수 없습니다."));
        post.increaseViews();
        postRepository.update(post);
        return PostResponse.of(post, getWriterResponse(post));
    }

    public List<PostResponse> findAll() {
        return postRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Post::getPostId)
                        .reversed())
                .map(post -> PostResponse.of(post, getWriterResponse(post)))
                .collect(Collectors.toList());
    }

    WriterResponse getWriterResponse(Post post) {
        return Optional.ofNullable(post.getWriterId())
                .flatMap(memberRepository::findById)
                .map(WriterResponse::from)
                .orElseThrow(() -> new NoSuchElementException("해당 id를 가진 글쓴이를 찾을 수 없습니다."));
    }
}
