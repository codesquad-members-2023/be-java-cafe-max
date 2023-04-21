package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostEditRequest;
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
        Member member = memberRepository.findByEmail(postWriteRequest.getWriterEmail()).orElseThrow();
        return postRepository.save(postWriteRequest.toMakePost(member), member);
    }

    public void editPost(PostEditRequest postEditRequest) {
        Post post = postRepository.findById(postEditRequest.getPostId())
                .orElseThrow(() -> new NoSuchElementException("해당 id를 가진 글을 찾을 수 없습니다."));
        post.editPost(postEditRequest.getTitle(), postEditRequest.getContent());
        postRepository.update(post);
    }


    public PostResponse findById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("해당 id를 가진 글을 찾을 수 없습니다."));
        post.increaseViews();
        postRepository.update(post);
        return PostResponse.of(post, getWriterResponse(post));
    }

    public List<Post> findPostByWriterEmail(String writerEmail) {
        return postRepository.findPostByWriterEmail(writerEmail);
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
        return Optional.ofNullable(post.getWriterEmail())
                .flatMap(memberRepository::findByEmail)
                .map(WriterResponse::from)
                .orElseThrow(() -> new NoSuchElementException("해당 id를 가진 글쓴이를 찾을 수 없습니다."));
    }
}
