package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import kr.codesqaud.cafe.exception.common.CommonException;
import kr.codesqaud.cafe.exception.common.CommonExceptionType;
import kr.codesqaud.cafe.repository.comment.CommentRepository;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;
import kr.codesqaud.cafe.session.LoginMemberSession;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Long save(PostWriteRequest postWriteRequest) {
        Member member = memberRepository.findByEmail(postWriteRequest.getWriterEmail()).orElseThrow();
        return postRepository.save(postWriteRequest.toMakePost(member), member);
    }

    @Transactional
    public void editPost(PostEditRequest postEditRequest, LoginMemberSession loginMemberSession) {
        Post post = postRepository.findById(postEditRequest.getid())
                .orElseThrow(() -> new NoSuchElementException("해당 id를 가진 글을 찾을 수 없습니다."));
        checkPostWriter(loginMemberSession, post);
        post.editPost(postEditRequest.getTitle(), postEditRequest.getContent());
        postRepository.update(post);
    }

    private void checkPostWriter(LoginMemberSession loginMemberSession, Post post) {
        if (loginMemberSession.isNotEqualMember(post.getWriterEmail())) {
            throw new CommonException(CommonExceptionType.ACCESS_DENIED);
        }
    }

    @Transactional
    public PostResponse findById(Long id) {
        postRepository.increaseViews(id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 id를 가진 글을 찾을 수 없습니다."));
        return PostResponse.of(post, getWriterResponse(post));
    }

    @Transactional(readOnly = true)
    public List<Post> findPostByWriterEmail(String writerEmail) {
        return postRepository.findPostByWriterEmail(writerEmail);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findAll() {
        return postRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Post::getid)
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
    @Transactional
    public void deleteId(Long id,LoginMemberSession loginMemberSession) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 id를 가진 글을 찾을 수 없습니다."));
        checkPostWriter(loginMemberSession, post);
        commentRepository.deletePostId(id);
        postRepository.deleteId(id);
    }
}
