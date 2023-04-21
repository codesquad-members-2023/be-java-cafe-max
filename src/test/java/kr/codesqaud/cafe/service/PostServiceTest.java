package kr.codesqaud.cafe.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostModifyRequest;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import kr.codesqaud.cafe.exception.post.PostNotFoundException;
import kr.codesqaud.cafe.repository.comment.CommentRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @Mock
    private CommentRepository commentRepository;

    @DisplayName("게시글 저장할 때 제목, 내용, 작성자 아이디를 입력하면 게시글이 저장되고 게시글 아이디를 반환한다")
    @Test
    void save() {
        // given
        Long writerId = 1L;
        PostWriteRequest postWriteRequest = new PostWriteRequest("제목", "내용", writerId);
        given(postRepository.save(any())).willReturn(1L);

        // when
        Long savedId = postService.write(postWriteRequest);

        // then
        assertEquals(1L, savedId);
    }

    @DisplayName("게시글 조회할 때 게시글이 있다면 게시글이 반환된다")
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
        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));

        // when
        PostResponse findPostResponse = postService.findById(1L);

        // then
        assertAll(
            () -> assertEquals(1L, findPostResponse.getId()),
            () -> assertEquals("제목", findPostResponse.getTitle()),
            () -> assertEquals("내용", findPostResponse.getContent()),
            () -> assertEquals(1L, findPostResponse.getWriter().getId()),
            () -> assertEquals(1L, findPostResponse.getViews()));
    }

    @DisplayName("게시글 조회할 때 게시글이 존재하지 않는다면 에러를 반환한다")
    @Test
    void findByIdFalse() {
        // given
        given(postRepository.findById(any())).willThrow(PostNotFoundException.class);

        // when

        // then
        assertThrows(PostNotFoundException.class, () -> postService.findById(1L));
    }

    @DisplayName("게시글 전체 조회할 때 게시글 있으면 모든 게시글을 반한환다")
    @Test
    void findAll() {
        // given
        given(postRepository.findAll()).willReturn(List.of(createPostDummy(), createPostDummy2()));

        // when
        List<PostResponse> findAll = postService.findAll();

        // then
        assertEquals(2, findAll.size());
    }

    @DisplayName("게시글 수정할 때 아이디, 제목, 내용 로그인 아이디를 입력하면 게시글이 수정된다")
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
        assertEquals("tset", findPost.getTitle());
        assertEquals("content", findPost.getContent());
    }

    @DisplayName("게시글 수정할 때 게시글이 없다면 에러를 반환한다")
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

    @DisplayName("게시글 수정할 때 게시글 작성자와 로그인 회원이 다르다면 에러를 반환한다")
    @Test
    void modifyFalse2() {
        // given
        PostModifyRequest postModifyRequest = new PostModifyRequest(1L, "tset", "content");
        given(postRepository.findById(any())).willReturn(Optional.of(createPostDummy()));

        // when

        // then
        assertThrows(UnauthorizedException.class,
            () -> postService.modify(postModifyRequest, 2L));
    }

    @DisplayName("게시글 삭제할 떄 게시글 아이디, 로그인 아이디를 입력하면 게시글이 삭제된다")
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
        assertThrows(PostNotFoundException.class, () -> postService.findById(savedId));
    }

    @DisplayName("게시글 삭제할 때 해당 게시글이 없으면 에러를 반환한다")
    @Test
    void deleteFalse() {
        // given
        Long savedId = 1L;
        Long accountSessionId = 1L;
        given(postRepository.findById(savedId)).willThrow(new PostNotFoundException());

        // when

        // then
        assertThrows(PostNotFoundException.class,
            () -> postService.delete(savedId, accountSessionId));
    }

    @DisplayName("게시글 삭제할 때 게시글 작성자와 로그인 회원이 다르면 에러를 반환한다")
    @Test
    void deleteFalse2() {
        // given
        Long savedId = 1L;
        Long accountSessionId = 2L;
        given(postRepository.findById(any())).willReturn(Optional.of(createPostDummy()));

        // when

        // then
        assertThrows(UnauthorizedException.class, () -> postService.delete(savedId, accountSessionId));
    }

    @DisplayName("게시글 삭제할 때 댓글 작성자와 로그인 회원이 다르면 에러를 반환한다")
    @Test
    void deleteFalse3() {
        // given
        Long savedId = 1L;
        Long accountSessionId = 1L;
        Comment comment = Comment.builder()
            .id(1L)
            .postId(1L)
            .writer(Member.builder().id(1L).build())
            .content("내용")
            .writeDate(LocalDateTime.now())
            .build();
        Comment comment2 = Comment.builder()
            .id(1L)
            .postId(1L)
            .content("내용")
            .writer(Member.builder().id(2L).build())
            .writeDate(LocalDateTime.now())
            .build();

        given(postRepository.findById(any())).willReturn(Optional.of(createPostDummy()));
        given(commentRepository.findAllByPostId(savedId))
            .willReturn(List.of(comment, comment2));

        // when

        // then
        assertThrows(UnauthorizedException.class, () -> postService.delete(savedId, accountSessionId));
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
            .writer(Member.builder().id(2L).build())
            .writeDate(LocalDateTime.now())
            .views(0L)
            .build();
    }
}
