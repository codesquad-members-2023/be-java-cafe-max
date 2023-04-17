package kr.codesqaud.cafe.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostModifyRequest;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import kr.codesqaud.cafe.exception.post.PostNotFoundException;
import kr.codesqaud.cafe.repository.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Spy
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @DisplayName("게시글 저장 성공")
    @Test
    void save() {
        // given
        Long writerId = 1L;
        PostWriteRequest postWriteRequest = new PostWriteRequest("제목", "내용", writerId);
        given(postRepository.save(any())).willReturn(writerId);

        // when
        Long savedId = postService.write(postWriteRequest);

        // then
        assertEquals(1L, savedId);
    }

    @DisplayName("게시글 단건 조회 작성자가 회원인 경우 성공")
    @Test
    void findById() {
        // given
        Post post = Post.builder()
            .id(1L)
            .title("제목")
            .content("내용")
            .writer(Member.builder().id(1L).build())
            .writeDate(LocalDateTime.now())
            .views(1L)
            .build();
        AccountSession accountSession = new AccountSession(1L);
        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));

        // when
        PostResponse findPostResponse = postService.findById(1L, accountSession.getId());

        // then
        assertAll(
            () -> assertEquals(1L, findPostResponse.getId()),
            () -> assertEquals("제목", findPostResponse.getTitle()),
            () -> assertEquals("내용", findPostResponse.getContent()),
            () -> assertEquals(1L, findPostResponse.getWriter().getId()),
            () -> assertEquals(1L, findPostResponse.getViews()));
    }

    @DisplayName("게시글 아이디가 존재하지 않은 아이디일 경우 단건 조회 실패")
    @Test
    void findByIdFalse() {
        // given
        given(postRepository.findById(any())).willThrow(PostNotFoundException.class);

        // when

        // then
        assertThrows(PostNotFoundException.class, () -> postService.findById(1L, 1L));
    }

    @DisplayName("게시글 작성자의 아이디가 존재하지 않은 회원 아이디일 경우 단건 조회 실패")
    @Test
    void findByIdFalse2() {
        // given
        given(postRepository.findById(any())).willReturn(Optional.empty());

        // when

        // then
        assertThrows(PostNotFoundException.class, () -> postService.findById(1L, 1L));
    }

    @DisplayName("비회원일때 게시글 단건 조회를 하면 예외가 발생한다.")
    @Test
    void findByIdFalse3() {
        // given
        Post post = createPostDummy();
        willThrow(new UnauthorizedException()).given(postService).validateUnauthorized(post.getId(), null);

        // when

        // then
        assertThrows(UnauthorizedException.class, () -> postService.findById(post.getId(), null));

    }

    @DisplayName("게시글 전체 조회 성공")
    @Test
    void findAll() {
        // given
        given(postRepository.findAll()).willReturn(List.of(createPostDummy(), createPostDummy2()));

        // when
        List<PostResponse> findAll = postService.findAll();

        // then
        assertEquals(2, findAll.size());
    }

    @DisplayName("게시글 수정 성공")
    @Test
    void modify() {
        // given
        PostModifyRequest postModifyRequest = new PostModifyRequest(1L, "tset", "content");
        given(postRepository.findById(postModifyRequest.getId()))
            .willReturn(Optional.of(Post.builder()
                .id(postModifyRequest.getId())
                .title(postModifyRequest.getTitle())
                .content(postModifyRequest.getContent())
                .writer(Member.builder()
                    .id(1L)
                    .build())
                .writeDate(LocalDateTime.now())
                .views(0L)
                .build()));

        // when
        postService.modify(postModifyRequest, postModifyRequest.getId());

        // then
        Post findPost = postRepository.findById(postModifyRequest.getId()).orElseThrow();
        assertEquals(postModifyRequest.getTitle(), findPost.getTitle());
        assertEquals(postModifyRequest.getContent(), findPost.getContent());
    }

    @DisplayName("게시글이 없는 경우 수정 했을때 실패")
    @Test
    void modifyFalse() {
        // given
        PostModifyRequest postModifyRequest = new PostModifyRequest(1L, "tset", "content");
        given(postRepository.findById(any())).willThrow(new PostNotFoundException());

        // when

        // then
        assertThrows(PostNotFoundException.class,
            () -> postService.modify(postModifyRequest, postModifyRequest.getId()));
    }

    @DisplayName("게시글 삭제 성공")
    @Test
    void delete() {
        // given
        Long savedId = 1L;
        Long accountSessionId = 1L;
        given(postRepository.findById(savedId)).willReturn(Optional.of(createPostDummy()))
            .willThrow(new PostNotFoundException());

        // when
        postService.delete(savedId, accountSessionId);

        // then
        assertThrows(PostNotFoundException.class, () -> postService.findById(savedId, accountSessionId));
    }

    @DisplayName("게시글 삭제시 해당 게시글이 없는 경우 실패")
    @Test
    void deleteFalse() {
        // given
        Long savedId = 1L;
        Long accountSessionId = 1L;
        given(postRepository.findById(savedId)).willThrow(new PostNotFoundException());

        // when

        // then
        assertThrows(PostNotFoundException.class, () -> postService.delete(savedId, accountSessionId));
    }

    private Post createPostDummy() {
        return Post.builder()
            .id(1L)
            .title("제목")
            .content("내용")
            .writer(Member.builder().id(1L).build())
            .writeDate(LocalDateTime.now())
            .views(0L)
            .build();
    }

    private Post createPostDummy2() {
        return Post.builder()
            .id(2L)
            .title("제목2")
            .content("내용2")
            .writer(Member.builder().id(1L).build())
            .writeDate(LocalDateTime.now())
            .views(0L)
            .build();
    }
}
