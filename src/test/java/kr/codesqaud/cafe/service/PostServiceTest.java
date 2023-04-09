package kr.codesqaud.cafe.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.exception.member.MemberNotFoundException;
import kr.codesqaud.cafe.exception.post.PostNotFoundException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    @DisplayName("게시글 저장 성공")
    @Test
    void save() {
        // given
        PostWriteRequest postWriteRequest = new PostWriteRequest("제목", "내용", 1L);
        given(postRepository.save(any())).willReturn(1L);

        // when
        Long savedId = postService.save(postWriteRequest);

        // then
        assertEquals(1L, savedId);
    }

    @DisplayName("게시글 단건 조회 작성자가 회원인 경우 성공")
    @Test
    void findById() {
        // given
        Long previousViews = 0L;
        Post post = createPostDummy();
        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        given(memberRepository.findById(1L)).willReturn(Optional.of(createMemberDummy()));
        willDoNothing().given(postRepository).update(any());

        // when
        PostResponse findPostResponse = postService.findById(1L);

        // then
        assertAll(() -> assertEquals(post.getId(), findPostResponse.getId()),
            () -> assertEquals(post.getTitle(), findPostResponse.getTitle()),
            () -> assertEquals(post.getContent(), findPostResponse.getContent()),
            () -> assertEquals(post.getWriterId(), findPostResponse.getWriter().getId()),
            () -> assertEquals(post.getWriteDate(), findPostResponse.getWriteDate()),
            () -> assertNotEquals(previousViews, findPostResponse.getViews()));
    }

    @DisplayName("게시글 단건 조회 작성자가 비회원인 경우 성공")
    @Test
    void findById2() {
        // given
        Post post = createPostDummy();
        Long previousViews = post.getViews();
        Member member = new Member(1L, "test@gmail.com", "Test1234", "test", LocalDateTime.now());
        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));
        willDoNothing().given(postRepository).update(any());

        // when
        PostResponse findPostResponse = postService.findById(1L);

        // then
        assertAll(() -> assertEquals(post.getId(), findPostResponse.getId()),
            () -> assertEquals(post.getTitle(), findPostResponse.getTitle()),
            () -> assertEquals(post.getContent(), findPostResponse.getContent()),
            () -> assertEquals(post.getWriterId(), findPostResponse.getWriter().getId()),
            () -> assertEquals(post.getWriteDate(), findPostResponse.getWriteDate()),
            () -> assertNotEquals(previousViews, findPostResponse.getViews()));
    }

    @DisplayName("게시글 아이디가 존재하지 않은 아이디일 경우 단건 조회 실패")
    @Test
    void findByIdFalse() {
        // given
        given(postRepository.findById(any())).willThrow(PostNotFoundException.class);

        // when

        // then
        assertThrows(PostNotFoundException.class, () -> postService.findById(1L));
    }

    @DisplayName("게시글 작성자의 아이디가 존재하지 않은 회원 아이디일 경우 단건 조회 실패")
    @Test
    void findByIdFalse2() {
        // given
        given(postRepository.findById(any())).willReturn(Optional.of(createPostDummy()));
        given(memberRepository.findById(any())).willThrow(MemberNotFoundException.class);

        // when

        // then
        assertThrows(MemberNotFoundException.class, () -> postService.findById(1L));
    }

    @DisplayName("게시글 전체 조회 성공")
    @Test
    void findAll() {
        // given
        given(postRepository.findAll()).willReturn(List.of(createPostDummy(), createPostDummy2()));
        given(memberRepository.findById(1L)).willReturn(Optional.of(createMemberDummy()));

        // when
        List<PostResponse> findAll = postService.findAll();

        // then
        assertEquals(2, findAll.size());
    }

    private Post createPostDummy() {
        return new Post(1L, "제목", "내용", 1L, LocalDateTime.now(), 0L);
    }

    private Post createPostDummy2() {
        return new Post(2L, "제목2", "내용2", null, LocalDateTime.now(), 0L);
    }

    private Member createMemberDummy() {
        return new Member(1L, "test@gmail.com", "Test1234", "test", LocalDateTime.now());
    }
}
